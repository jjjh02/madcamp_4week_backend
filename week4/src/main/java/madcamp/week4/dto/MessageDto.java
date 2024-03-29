package madcamp.week4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
public class MessageDto {
    private String fromNickName;
    private String messageDescription;
    private LocalDateTime messageTime;
    private Boolean isRead;
    private Long toId;
    private Long fromId;
    private Long organizationId;

    public MessageDto(){

    }

    public MessageDto(String fromNickName, String messageDescription, LocalDateTime messageTime, Boolean isRead, Long toId, Long fromId, Long organizationId) {
        this.fromNickName = fromNickName;
        this.messageDescription = messageDescription;
        this.messageTime = messageTime;
        this.isRead = isRead;
        this.toId = toId;
        this.fromId = fromId;
        this.organizationId = organizationId;
    }
}