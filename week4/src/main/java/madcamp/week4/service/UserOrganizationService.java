package madcamp.week4.service;

import lombok.RequiredArgsConstructor;
import madcamp.week4.model.Organization;
import madcamp.week4.model.User;
import madcamp.week4.repository.OrganizationRepository;
import madcamp.week4.repository.UserOrganizationRepository;
import madcamp.week4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrganizationService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    public void removeUserFromOrganization(Long userId, Long organizationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        user.removeOrganization(organization);
        organization.removeUser(user);

        userRepository.save(user);
        organizationRepository.save(organization);
    }
}
