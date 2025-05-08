package madcamp.week4.service;

import lombok.RequiredArgsConstructor;
import madcamp.week4.dto.OrganizationResponseDto;
import madcamp.week4.dto.OrganizationUpdateRequestDto;
import madcamp.week4.dto.UserResponseDto;
import madcamp.week4.model.Organization;
import madcamp.week4.model.Role;
import madcamp.week4.model.User;
import madcamp.week4.model.UserOrganization;
import madcamp.week4.repository.OrganizationRepository;
import madcamp.week4.repository.UserOrganizationRepository;
import madcamp.week4.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final InviteCodeService inviteCodeService;

    public static String generateInviteCode() {
        return UUID.randomUUID().toString()
                .replace("-", "") // 하이픈 제거
                .substring(0, 8)  // 앞 8자리만 사용
                .toUpperCase();   // 대문자로 보기 좋게
    }

    // 그룹 생성
    public void createOrganization(Long userId, String organizationName) {
        User user = userRepository.findById(userId).orElse(null);
        // 그룹 초대코드 생성 -> redis 사용 고민
        Organization newOrganization = Organization.builder().organizationName(organizationName).build();
        // ADMIN 설정
        UserOrganization newUserOrganization = UserOrganization.builder().user(user).organization(newOrganization).role(Role.ADMIN).build();

        organizationRepository.save(newOrganization);
        userOrganizationRepository.save(newUserOrganization);
    }

    // 그룹 탈퇴
    public void leaveOrganization(Long userId, Long organizationId) {
        UserOrganization userOrganization = userOrganizationRepository
                .findByUserUserIdAndOrganizationOrganizationId(userId, organizationId).orElseThrow(() -> new IllegalArgumentException("해당 조직에 속한 사용자를 찾을 수 없습니다."));
        if(userOrganization.isAdmin()) {
            throw new IllegalStateException("ADMIN은 탈퇴할 수 없습니다. 권한을 위임한 후 탈퇴하세요.");
        }
        userOrganizationRepository.delete(userOrganization);
    }

    // 그룹 입장
    public void enterOrganization(Long userId, String inviteCode) {
        Long organizationId = inviteCodeService.getOrganizationIdByInviteCode(inviteCode);
        if (organizationId == null) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 초대코드입니다.");
        }

        User user = userRepository.findById(userId).orElse(null);
        Organization organization = organizationRepository.findById(organizationId).orElse(null);

        UserOrganization userOrganization = UserOrganization.builder()
                .user(user)
                .organization(organization)
                .role(Role.MEMBER)
                .build();

        userOrganizationRepository.save(userOrganization);

        // 일회성 사용이라면 코드 삭제
        inviteCodeService.removeInviteCode(inviteCode);
    }

    // 그룹 삭제
    public void deleteOrganization(Long userId, Long organizationId) {
        UserOrganization userOrganization = userOrganizationRepository
                .findByUserUserIdAndOrganizationOrganizationId(userId, organizationId).orElseThrow(() -> new IllegalArgumentException("해당 조직에 속한 사용자를 찾을 수 없습니다."));
        if(userOrganization.isMember()) {
            throw new IllegalStateException("MEMBER는 그룹을 삭제할 수 없습니다.");
        }
        organizationRepository.deleteById(organizationId);
    }

    // 그룹 수정
    public void updateOrganization(Long userId, OrganizationUpdateRequestDto organizationUpdateRequestDto) {
        UserOrganization userOrganization = userOrganizationRepository
                .findByUserUserIdAndOrganizationOrganizationId(userId, organizationUpdateRequestDto.getOrganizationId()).orElseThrow(() -> new IllegalArgumentException("해당 조직에 속한 사용자를 찾을 수 없습니다."));
        if(userOrganization.isMember()) {
            throw new IllegalStateException("MEMBER는 그룹을 수정할 수 없습니다.");
        }
        Organization organization = userOrganization.getOrganization();
        organization.changeOrganizationName(organizationUpdateRequestDto);
    }

    // 특정 그룹 조회
    public OrganizationResponseDto getOrganizationById(Long userId, Long organizationId) {
        UserOrganization userOrganization = userOrganizationRepository
                .findByUserUserIdAndOrganizationOrganizationId(userId, organizationId).orElseThrow(() -> new IllegalArgumentException("해당 조직에 속한 사용자를 찾을 수 없습니다."));
        Organization organization = userOrganization.getOrganization();
        return toOranizationReponseDto(organization);
    }

    // 그룹 조회
    public List<OrganizationResponseDto> getOrganizationsByUserId(Long userId) {
        List<UserOrganization> userOrganizations = userOrganizationRepository.findByUserUserId(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 속한 조직을 찾을 수 없습니다."));
        List<Organization> organizations = userOrganizations.stream()
                .map(UserOrganization::getOrganization)
                .toList();
        return organizations.stream()
                .map(this::toOranizationReponseDto)
                .toList();
    }

    // 그룹 참여자 조회
    public List<UserResponseDto> getUsersByOrganizationId(Long organizationId) {
        List<UserOrganization> userOrganizations = userOrganizationRepository.findByUserUserId(organizationId).orElseThrow(() -> new IllegalArgumentException("해당 조직에 속한 사용자를 찾을 수 없습니다."));
        List<User> users = userOrganizations.stream()
                .map(UserOrganization::getUser)
                .toList();
        return users.stream()
                .map(this::toUserResponseDto)
                .toList();
    }

    private OrganizationResponseDto toOranizationReponseDto(Organization organization) {
        return new OrganizationResponseDto(
                organization.getOrganizationId(),
                organization.getOrganizationName()
        );
    }

    private UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getUserName(),
                user.getName()
        );
    }

}

