package madcamp.week4.dto;

import lombok.Getter;

@Getter
public class UserOrganizationRequest {
    private Long userId;
    private Long organizationId;

    public UserOrganizationRequest(){

    }
}
