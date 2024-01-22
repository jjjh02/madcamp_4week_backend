package madcamp.week4.dto;

import lombok.Getter;

@Getter
public class OrganizationJoinRequest {
    private Long userId;
    private String organizationInviteNumber;

    public OrganizationJoinRequest(){

    }
}