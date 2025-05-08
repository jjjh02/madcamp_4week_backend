package madcamp.week4.dto;

import lombok.Getter;

@Getter
public class OrganizationResponseDto {
    private Long organizationId;
    private String organizationName;

    public OrganizationResponseDto(Long organizationId, String organizationName) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

}
