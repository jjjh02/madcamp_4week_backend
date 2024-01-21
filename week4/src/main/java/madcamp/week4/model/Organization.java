package madcamp.week4.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;


    private String groupName;


    private String groupInviteNumber;


    @ManyToMany(mappedBy = "organizations")
    private List<User> users;
    public Organization() {
    }


}