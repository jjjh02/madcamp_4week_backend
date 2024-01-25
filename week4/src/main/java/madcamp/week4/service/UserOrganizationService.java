package madcamp.week4.service;

import madcamp.week4.model.Organization;
import madcamp.week4.model.User;
import madcamp.week4.repository.OrganizationRepository;
import madcamp.week4.repository.UserOrganizationRepository;
import madcamp.week4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOrganizationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

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
