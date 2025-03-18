package madcamp.week4.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import madcamp.week4.util.UniqueStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

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


    private String organizationInviteNumber;

    public void makeOrganization(String organizationName){
        this.organizationName = organizationName;
    }


}