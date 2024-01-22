package madcamp.week4.controller;

import madcamp.week4.dto.OrganizationCreateRequest;
import madcamp.week4.dto.OrganizationIdRequest;
import madcamp.week4.dto.UserResponseDto;
import madcamp.week4.model.Organization;
import madcamp.week4.model.User;
import madcamp.week4.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/organization")
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;
    // 방생성 (초대코드 받아오기)
    @PostMapping("/create")
    public ResponseEntity<Organization> createOrganization(@RequestBody OrganizationCreateRequest request) {
        Organization newOrganization = new Organization();
        newOrganization.makeOrganization(request.getOrganizationName());
        Organization createdOrganization = organizationService.createOrganization(newOrganization);
        return ResponseEntity.ok(createdOrganization);
    }

    // 특정 그룹을 눌렀을때 해당하는 사람들의 리스트의 파일이 모두 떠야함
    @PostMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getUsersByOrganization(@RequestBody OrganizationIdRequest request) {
        List<User> users = organizationService.getUsersByOrganizationId(request.getOrganizationId());
        List<UserResponseDto> userDtos = users.stream()
                .map(user -> new UserResponseDto(user.getUserId(), user.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }


    //    public ResponseEntity<List<User>> getUsersByOrganization(@RequestBody OrganizationIdRequest request) {
//        List<User> users = organizationService.getUsersByOrganizationId(request.getOrganizationId());
//        return ResponseEntity.ok(users);
//    }

}
