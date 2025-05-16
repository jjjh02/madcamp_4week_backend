package madcamp.week4.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madcamp.week4.dto.UserRequestDto;
import madcamp.week4.jwt.JwtProvider;
import madcamp.week4.model.User;
import madcamp.week4.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    // token에서 유저 아이디 추출
    public Long getUserIdFromToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7); // "Bearer "를 제외한 토큰
        return jwtProvider.extractUserId(accessToken).orElseThrow(() -> new RuntimeException("토큰에서 유저 아이디를 찾을 수 없습니다."));
    }

    // 회원가입
    public void signUp(UserRequestDto userRequestDto, HttpServletResponse response) {
        // 아이디 중복 체크
        if (userRepository.findUserByUserName(userRequestDto.getUserName()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        // 저장
        User user = User.builder()
                .userName(userRequestDto.getUserName())
                .password(passwordEncoder.encode(userRequestDto.getPassword())) // 비밀번호 암호화
                .name(userRequestDto.getName())
                .build();

        userRepository.save(user);

        // 가입하자마자 로그인 토큰 발급
        String accessToken = jwtProvider.createAccessToken(user.getUserName(), user.getUserId());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId());
        jwtProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
    }

    // 로그인
    public void login(UserRequestDto userRequestDto, HttpServletResponse response) {
        User user = userRepository.findUserByUserName(userRequestDto.getUserName())
                .orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(user.getUserName(), user.getUserId());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId());
        jwtProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
    }



}

