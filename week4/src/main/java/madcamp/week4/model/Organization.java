package madcamp.week4.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import madcamp.week4.util.UniqueStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

@Entity
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "organizationId")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;


    private String organizationName;


    private String organizationInviteNumber;

    @ManyToMany(mappedBy = "organizations")
    private List<User> users;

    public Organization() {
        this.organizationInviteNumber = UniqueStringGenerator.generateUniqueString();
    }

    public void makeOrganization(String organizationName){
        this.organizationName = organizationName;
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getOrganizations().remove(this);
    }

}