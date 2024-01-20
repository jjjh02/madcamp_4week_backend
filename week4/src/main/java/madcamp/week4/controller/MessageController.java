package madcamp.week4.controller;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.extern.slf4j.Slf4j;
import madcamp.week4.dto.MessageDto;
import madcamp.week4.model.Message;
import madcamp.week4.model.User;
import madcamp.week4.service.MessageService;
import madcamp.week4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // 특정 id를 받을 때 그에 해당하는 메시지가 떠야함
    // 한 사람의 폴더를 눌렀을때 그 사람이 받은 메시지가 다 뜨도록 하는 함수
    @GetMapping("/show/{userId}")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Long userId) {
        List<Message> messages = messageService.listMessagesByUser(userId);

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build(); // 해당 사용자의 메시지가 없음을 나타내는 204 No Content 반환
        } else {
            return ResponseEntity.ok(messages); // 해당 사용자의 메시지를 반환
        }
    }

    // 사용자가 message를 적었을 때 적은 정보와 id를 받아와서 저장하는 함수
    @PostMapping("/write")
    public ResponseEntity<Message> writeMessage(@RequestBody Message message){
        Message writtenMessage = messageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(writtenMessage);
    }


}
