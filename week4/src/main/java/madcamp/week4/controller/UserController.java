package madcamp.week4.controller;

import madcamp.week4.model.User;
import madcamp.week4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> logInUser(@RequestBody User user) throws LoginException {
        User loggedinUser = userService.loginUser(user.getUserName(), user.getPassword());
        return ResponseEntity.ok(loggedinUser);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody User user){
        User signupedUser = userService.signupUser(user);
        return ResponseEntity.ok(signupedUser);
    }


}
