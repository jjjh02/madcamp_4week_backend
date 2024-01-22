package madcamp.week4.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageResponseDto {
    private Long messageId;
    private String fromNickName;
    private String messageDescription;
    private LocalDateTime messageTime;
    private Boolean isRead;
    private Long toId;
    private Long fromId;
    private Long organizationId;

    private String organizationName;

    public MessageResponseDto(){

    }
    public MessageResponseDto(Long messageId, String fromNickName, String messageDescription, LocalDateTime messageTime, Boolean isRead, Long toId, Long fromId, Long organizationId, String organizationName) {
        this.messageId = messageId;
        this.fromNickName = fromNickName;
        this.messageDescription = messageDescription;
        this.messageTime = messageTime;
        this.isRead = isRead;
        this.toId = toId;
        this.fromId = fromId;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

}
