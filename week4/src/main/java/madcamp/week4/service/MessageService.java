package madcamp.week4.service;

import madcamp.week4.model.Message;
import madcamp.week4.model.User;
import madcamp.week4.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> listMessagesByUser(Long userId){
        return messageRepository.findByToIdUserId(userId);
    }


}
