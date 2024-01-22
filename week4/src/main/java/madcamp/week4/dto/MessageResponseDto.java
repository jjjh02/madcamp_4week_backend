package madcamp.week4.dto;

import java.time.LocalDateTime;

public class MessageResponseDto {
    private Long messageId;
    private String fromNickName;
    private String messageDescription;
    private LocalDateTime messageTime;
    private Boolean isRead;
    private Long toId;
    private Long fromId;
    private Long organizationId;

    public MessageResponseDto(Long messageId, String fromNickName, String messageDescription, LocalDateTime messageTime, Boolean isRead, Long toId, Long fromId, Long organizationId) {
        this.messageId = messageId;
        this.fromNickName = fromNickName;
        this.messageDescription = messageDescription;
        this.messageTime = messageTime;
        this.isRead = isRead;
        this.toId = toId;
        this.fromId = fromId;
        this.organizationId = organizationId;
    }

    // Getters and Setters
    // ...

    // messageId를 포함한 다른 필드의 getter 및 setter 메서드들
}
