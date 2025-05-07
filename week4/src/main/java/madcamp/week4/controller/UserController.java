package madcamp.week4.controller;

import lombok.RequiredArgsConstructor;
import madcamp.week4.dto.OrganizationJoinRequest;
import madcamp.week4.dto.OrganizationResponseDto;
import madcamp.week4.dto.UserIdRequest;
import madcamp.week4.model.Organization;
import madcamp.week4.model.User;
import madcamp.week4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

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

    // organization에 들어가기
//    @PostMapping("/joinOrganization")
//    public ResponseEntity<User> joinOrganization(@RequestBody OrganizationJoinRequest joinRequest) {
//        User updatedUser = userService.joinOrganization(joinRequest.getUserId(), joinRequest.getOrganizationInviteNumber());
//        return ResponseEntity.ok(updatedUser);
//    }
//
//    // user가 들어가있는 모든 그룹 보여주기
//    @PostMapping("/organizations")
//    public List<OrganizationResponseDto> getUserOrganizations(@RequestBody UserIdRequest request) {
//        return userService.getOrganizationsByUserId(request.getUserId());
//    }
//    public ResponseEntity<List<Organization>> getUserOrganizations(@RequestBody UserIdRequest request) {
//        List<Organization> organizations = userService.getUserOrganizations(request.getUserId());
//        return ResponseEntity.ok(organizations);
//    }




}
