package madcamp.week4.service;

import madcamp.week4.exception.CustomException;
import madcamp.week4.model.User;
import madcamp.week4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signupUser(User user) {
        Optional<User> existingUser = userRepository.findUserByUserName(user.getUserName());
        if (existingUser.isPresent()) {
            // 동일한 userName을 가진 사용자가 이미 존재하는 경우
            // 여기에 적절한 예외 처리나 다른 로직을 구현합니다.
            throw new CustomException("User with username " + user.getUserName() + " already exists.");
        }
        return userRepository.save(user);
    }


    public User loginUser(String userName, String password) throws LoginException {
        Optional<User> userOptional = userRepository.findUserByUserName(userName);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 사용자가 입력한 비밀번호와 저장된 비밀번호를 비교
            if (password.equals(user.getPassword())) {
                return user; // 로그인 성공
            }
        }

        throw new LoginException("로그인 실패"); // 로그인 실패 시 예외를 던집니다.
    }

}
