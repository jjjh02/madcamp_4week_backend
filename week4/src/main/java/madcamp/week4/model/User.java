package madcamp.week4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class User {
    @Id
    private Long id;

    private String userName;

    private String password;

}
