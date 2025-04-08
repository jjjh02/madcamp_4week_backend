package madcamp.week4.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String fromNickName;

    private String messageDescription;

    private LocalDateTime messageTime;

    private Boolean isRead;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_id", referencedColumnName = "userId")
    private User receiver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_id", referencedColumnName = "userId")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "organizationId")
    private Organization organization;

    public void markAsRead(Boolean read) {
        isRead = read;
    }
}
