package madcamp.week4.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import madcamp.week4.dto.OrganizationUpdateRequestDto;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "organizationId")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    private String organizationName;

    public void changeOrganizationName(OrganizationUpdateRequestDto organizationUpdateRequestDto) { this.organizationName = organizationUpdateRequestDto.getNewOrganizationName(); }
}