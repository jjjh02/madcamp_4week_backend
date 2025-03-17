package madcamp.week4.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import madcamp.week4.dto.MessageDto;
import madcamp.week4.dto.MessageResponseDto;
import madcamp.week4.model.Message;
import madcamp.week4.model.Organization;
import madcamp.week4.model.User;
import madcamp.week4.repository.MessageRepository;
import madcamp.week4.repository.OrganizationRepository;
import madcamp.week4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> listMessagesByUser(Long userId){
        return messageRepository.findByToIdUserId(userId);
    }

    public List<Message> getMessagesByUserAndOrganization(Long userId, Long organizationId) {
        return messageRepository.findByToIdUserIdAndOrganizationOrganizationId(userId, organizationId);
    }

    public Message createAndSaveMessage(MessageDto messageDto) {
        User toUser = userRepository.findById(messageDto.getToId()).orElse(null);
        User fromUser = userRepository.findById(messageDto.getFromId()).orElse(null);
        Organization organization = organizationRepository.findById(messageDto.getOrganizationId()).orElse(null);

        Message message = new Message();
        message.createMessage(messageDto.getFromNickName(), messageDto.getMessageDescription(), messageDto.getMessageTime(), messageDto.getIsRead());
        message.setUsersAndOrganization(toUser, fromUser, organization);

        return messageRepository.save(message);
    }

    public Message readMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));

        message.changeRead(true);
        return messageRepository.save(message);
    }

    public MessageResponseDto createMessageResponseDto(Message message) {
        return new MessageResponseDto(
                message.getMessageId(),
                message.getFromNickName(),
                message.getMessageDescription(),
                message.getMessageTime(),
                message.getIsRead(),
                message.getToId() != null ? message.getToId().getUserId() : null,
                message.getFromId() != null ? message.getFromId().getUserId() : null,
                message.getOrganization() != null ? message.getOrganization().getOrganizationId() : null,
                message.getOrganization() != null ? message.getOrganization().getOrganizationName() : null
        );
    }

}
