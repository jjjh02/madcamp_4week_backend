package madcamp.week4.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String fromNickName;

    private String messageDescription;

    private LocalDateTime messageTime;

    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "to_id", referencedColumnName = "userId")
    private User toId;

    @ManyToOne
    @JoinColumn(name = "from_id", referencedColumnName = "userId")
    private User fromId;

    public Message() {

    }



}
