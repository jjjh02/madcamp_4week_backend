package madcamp.week4.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import madcamp.week4.util.UniqueStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

@Entity
@Getter
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;


    private String organizationName;


    private String organizationInviteNumber;


    @JsonManagedReference
    @ManyToMany(mappedBy = "organizations")
    private List<User> users;

    public Organization() {
        this.organizationInviteNumber = UniqueStringGenerator.generateUniqueString();
    }

    public void makeOrganization(String organizationName){
        this.organizationName = organizationName;
    }

}