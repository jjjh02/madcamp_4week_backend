package madcamp.week4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madcamp.week4.dto.*;
import madcamp.week4.model.Message;
import madcamp.week4.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    // 특정 id를 받을 때 그에 해당하는 메시지가 떠야함
    // 한 사람의 폴더를 눌렀을때 그 사람이 받은 메시지가 다 뜨도록 하는 함수
    @PostMapping("/show")
    public ResponseEntity<List<MessageResponseDto>> getMessagesByUserId(@RequestBody UserIdRequest request) {
        List<Message> messages = messageService.listMessagesByUser(request.getUserId());

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<MessageResponseDto> messageResponseDtos = new ArrayList<>();

            for (Message message : messages) {
                MessageResponseDto responseDto = new MessageResponseDto(
                        message.getMessageId(),
                        message.getFromNickName(),
                        message.getMessageDescription(),
                        message.getMessageTime(),
                        message.getIsRead(),
                        message.getReceiver().getUserId(),
                        message.getSender().getUserId(),
                        message.getOrganization().getOrganizationId(),
                        message.getOrganization().getOrganizationName()
                );

                messageResponseDtos.add(responseDto);
            }

            return ResponseEntity.ok(messageResponseDtos);
        }
    }

    // 사용자가 message를 적었을 때 적은 정보와 id를 받아와서 저장하는 함수
    @PostMapping("/write")
    public ResponseEntity<Message> writeMessage(@RequestBody MessageDto messageDto) {
        Message writtenMessage = messageService.createAndSaveMessage(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(writtenMessage);
    }

    @PutMapping("/read")
    public ResponseEntity<Message> readMessage(@RequestBody MessageIdRequest messageIdRequest) {
        Message updatedMessage = messageService.readMessage(messageIdRequest.getMessageId());
        return ResponseEntity.ok(updatedMessage);
    }

    // 해당 user의 특정 group에 대한 메시지 리스트
    @PostMapping("/group")
    public ResponseEntity<List<MessageResponseDto>> getMessagesByUserAndOrganization(
            @RequestBody UserOrganizationRequest request) {
        List<Message> messages = messageService.getMessagesByUserAndOrganization(
                request.getUserId(),
                request.getOrganizationId());
        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<MessageResponseDto> messageResponseDtos = new ArrayList<>();

            for (Message message : messages) {
                MessageResponseDto responseDto = new MessageResponseDto(
                        message.getMessageId(),
                        message.getFromNickName(),
                        message.getMessageDescription(),
                        message.getMessageTime(),
                        message.getIsRead(),
                        message.getReceiver().getUserId(),
                        message.getSender().getUserId(),
                        message.getOrganization().getOrganizationId(),
                        message.getOrganization().getOrganizationName()
                );

                messageResponseDtos.add(responseDto);
            }

            return ResponseEntity.ok(messageResponseDtos);
        }
    }
}
