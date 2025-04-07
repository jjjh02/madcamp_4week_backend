package madcamp.week4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class OrganizationUpdateRequestDto {
    private Long organizationId;
    private String newOrganizationName;
}
