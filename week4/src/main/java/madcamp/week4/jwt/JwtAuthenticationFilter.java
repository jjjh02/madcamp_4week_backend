package madcamp.week4.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madcamp.week4.model.RefreshToken;
import madcamp.week4.model.User;
import madcamp.week4.repository.UserRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> NO_CHECK_URLS = List.of("/api/users/login", "/swagger-ui", "/api/users/signup", "/v3/api-docs");

    public final JwtProvider jwtProvider;
    public final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUrl = request.getRequestURI();

        // NO_CHECK_URLS에 해당 requestUrl이 있는 경우
        // JWT 검증 로직 없이 filter 통과
        if (NO_CHECK_URLS.stream().anyMatch(requestUrl::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken= jwtProvider.extractAccessToken(request).orElse(null);
        String refreshToken = jwtProvider.extractRefreshToken(request).orElse(null);

        try{
            // token이 비어있음
            if(accessToken == null && refreshToken == null) {
                log.error("액세스 토큰과 리프레시 토큰이 모두 존재하지 않습니다.");
                throw new JWTVerificationException("액세스 토큰과 리프레시 토큰이 모두 존재하지 않습니다.");
            }
            // accesstoken 유효
            if(accessToken != null && jwtProvider.isAccessTokenValid(accessToken)) {
                checkAccessTokenAndAuthentication(request, response, filterChain);
            }
            // refreshtoken 유효 -> accesstoken 재발급
            if(refreshToken != null) {
                reIssueAccessToken(response, refreshToken);
                return;
            }
        }
        catch (JWTVerificationException e) {
            log.error("JWT 검증 실패: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String errorMessage = String.format("{\"errorCode\": \"%d\", \"error\": \"%s\"}", e.getMessage());
            response.getWriter().write(errorMessage);
        }

    }

    public void reIssueAccessToken(HttpServletResponse response, String refreshToken) throws IOException {
        log.info("reIssueAccessToken() 호출");
        User user = jwtProvider.validateAndGetUserByRefreshToken(refreshToken);
        log.info("기존 리프레시토큰, oldRefreshToken = {}", refreshToken);
        String newRefreshToken = jwtProvider.rotateRefreshToken(refreshToken);
        log.info("새로운 리프레시토큰, newRefreshToken = {}", newRefreshToken);
        jwtProvider.sendAccessAndRefreshToken(response, jwtProvider.createAccessToken(user.getUserName(), user.getUserId()), newRefreshToken);
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");

        jwtProvider.extractAccessToken(request)
                .ifPresent(accessToken -> {
                    jwtProvider.extractUserId(accessToken)
                            .ifPresent(userId -> {
                                log.info("인증 대상 userId = {}", userId);
                                userRepository.findByUserId(userId)
                                        .ifPresentOrElse(this::saveAuthentication, () -> {
                                            log.warn("토큰은 유효하지만 userId={}에 해당하는 유저 없음", userId);
                                        });
                            });
                });

        // ✅ 인증 정보가 SecurityContext에 저장되지 않은 경우 → 403 반환
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("SecurityContext에 인증 정보가 없습니다. 403 Forbidden 반환");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"error\": \"인증 실패: 인증 정보 없음\"}");
            return;
        }

        // 인증 성공한 경우에만 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }

    // 인증 허가
    public void saveAuthentication(User myUser) {
        log.info("saveAuthentication() 호출");
        String password = myUser.getPassword();

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getUserName())
                .password(password)
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
