package madcamp.week4.dto;

import lombok.Getter;

@Getter
public class OrganizationResponseDto {
    private Long organizationId;
    private String organizationName;
    private String organizationInviteNumber;

    public OrganizationResponseDto(Long organizationId, String organizationName, String organizationInviteNumber) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationInviteNumber = organizationInviteNumber;
    }

}
