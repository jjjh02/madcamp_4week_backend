package madcamp.week4.dto;

import java.time.LocalDateTime;

public class MessageDto {
    private String fromNickName;

    private String messageDescription;

    private LocalDateTime messageTime;

    public MessageDto(){

    }

    public void applyInputMessageValue(MessageDto messageDto){
        this.fromNickName = messageDto.fromNickName;
        this.messageDescription = messageDto.messageDescription;
        this.messageTime = messageDto.messageTime;
    }

}
