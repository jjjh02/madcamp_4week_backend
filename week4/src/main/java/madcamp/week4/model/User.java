package madcamp.week4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;

// default
@Entity
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 생성해주는 id
    private Long userId;

    private String userName;

    private String password;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_organization", // Name of the join table
            joinColumns = @JoinColumn(name = "userId"), // Column for User
            inverseJoinColumns = @JoinColumn(name = "groupId") // Column for Organization
    )
    private List<Organization> organizations;

    // 생성자
    public User() {

    }


}
