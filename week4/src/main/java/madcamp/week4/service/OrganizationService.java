package madcamp.week4.service;

import jakarta.persistence.EntityNotFoundException;
import madcamp.week4.model.Organization;
import madcamp.week4.model.User;
import madcamp.week4.repository.OrganizationRepository;
import madcamp.week4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {





    @Autowired
    private OrganizationRepository organizationRepository;

    // 방생성 (초대코드 받아오기)
    public Organization createOrganization(Organization organization){return organizationRepository.save(organization);}

    // 방들어가기
    public Organization getOrganizationByInvitationNumber(String organizationInviteNumber) {
        return organizationRepository.findByOrganizationInviteNumber(organizationInviteNumber)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with invite number: " + organizationInviteNumber));
    }

    // 특정 그룹을 눌렀을때 해당하는 사람들의 리스트의 파일이 모두 떠야함
    public List<User> getUsersByOrganizationId(Long organizationId) {
        return organizationRepository.findById(organizationId)
                .map(Organization::getUsers)
                .orElse(Collections.emptyList()); // 조직이 존재하지 않으면 빈 리스트 반환
    }

    public Organization findByInviteNumber(String inviteNumber) {
        return organizationRepository.findByOrganizationInviteNumber(inviteNumber).orElse(null);
    }

}
