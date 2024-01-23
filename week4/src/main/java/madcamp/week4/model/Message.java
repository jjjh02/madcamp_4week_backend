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

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "organizationId")
    private Organization organization;

    public Message() {

    }

    public void createMessage(String fromNickName, String messageDescription, LocalDateTime messageTime, Boolean isRead){
        this.fromNickName = fromNickName;
        this.messageDescription = messageDescription;
        this.messageTime = messageTime;
        this.isRead = isRead;
    }

    public void setUsersAndOrganization(User toUser, User fromUser, Organization organization) {
        this.toId = toUser;
        this.fromId = fromUser;
        this.organization = organization;
    }

    public void changeRead(Boolean read) {
        isRead = read;
    }
}
