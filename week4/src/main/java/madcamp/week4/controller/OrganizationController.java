package madcamp.week4.controller;

import lombok.RequiredArgsConstructor;
import madcamp.week4.service.InviteCodeService;
import madcamp.week4.service.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final InviteCodeService inviteCodeService;

    // 초대코드 생성하기
    @PostMapping("/{organizationId}/invite-code")
    public ResponseEntity<String> generateInviteCode(@PathVariable Long organizationId) {
        String code = inviteCodeService.generateAndStoreInviteCode(organizationId);
        return ResponseEntity.ok(code);
    }
    // 방생성 (초대코드 받아오기)
//    @PostMapping("/create")
//    public ResponseEntity<Organization> createOrganization(@RequestBody OrganizationCreateRequest request) {
//        Organization newOrganization = new Organization();
//        newOrganization.makeOrganization(request.getOrganizationName());
//        Organization createdOrganization = organizationService.createOrganization(newOrganization);
//        return ResponseEntity.ok(createdOrganization);
//    }

    // 특정 그룹을 눌렀을때 해당하는 사람들의 리스트의 파일이 모두 떠야함
//    @PostMapping("/users")
//    public ResponseEntity<List<UserResponseDto>> getUsersByOrganization(@RequestBody OrganizationIdRequest request) {
//        List<User> users = organizationService.getUsersByOrganizationId(request.getOrganizationId());
//        List<UserResponseDto> userDtos = users.stream()
//                .map(user -> new UserResponseDto(user.getUserId(), user.getName()))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(userDtos);
//    }


    //    public ResponseEntity<List<User>> getUsersByOrganization(@RequestBody OrganizationIdRequest request) {
//        List<User> users = organizationService.getUsersByOrganizationId(request.getOrganizationId());
//        return ResponseEntity.ok(users);
//    }

}
