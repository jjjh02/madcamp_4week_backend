package madcamp.week4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Post {

    @Id
    private Long PostId;

    private String postTitle;

    private String postDescription;
}