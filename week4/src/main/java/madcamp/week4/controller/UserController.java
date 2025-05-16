package madcamp.week4.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import madcamp.week4.dto.UserRequestDto;
import madcamp.week4.model.User;
import madcamp.week4.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> logInUser(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) throws LoginException {
        userService.login(userRequestDto, response);
        return ResponseEntity.ok("로그인 성공!");
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        userService.signUp(userRequestDto, response);
        return ResponseEntity.ok("회원가입 성공!");
    }


}
