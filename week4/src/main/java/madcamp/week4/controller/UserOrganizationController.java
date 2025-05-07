package madcamp.week4.controller;

import madcamp.week4.model.Organization;
import madcamp.week4.model.User;
import madcamp.week4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-organization")
public class UserOrganizationController {

//    @Autowired
//    private UserOrganizationService userOrganizationService;
//
//    @DeleteMapping("/remove")
//    public ResponseEntity<?> removeUserFromOrganization(@RequestParam Long userId, @RequestParam Long organizationId) {
//        userOrganizationService.removeUserFromOrganization(userId, organizationId);
//        return ResponseEntity.ok().build();
//    }
}
