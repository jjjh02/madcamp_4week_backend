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

    // message 조회 (by user)
    public List<Message> listMessagesByUser(Long userId){
        return messageRepository.findByReceiverUserId(userId);
    }

    // message 조회 (by user, organization)
    public List<Message> getMessagesByUserAndOrganization(Long userId, Long organizationId) {
        return messageRepository.findByReceiverUserIdAndOrganizationOrganizationId(userId, organizationId);
    }

    // message 생성
    public Message createAndSaveMessage(MessageDto messageDto) {
        User receiver = userRepository.findById(messageDto.getReceiver()).orElse(null);
        User sender = userRepository.findById(messageDto.getSender()).orElse(null);
        Organization organization = organizationRepository.findById(messageDto.getOrganizationId()).orElse(null);

        Message message = Message.builder()
                .fromNickName(messageDto.getFromNickName())
                .messageDescription(messageDto.getMessageDescription())
                .messageTime(messageDto.getMessageTime())
                .isRead(messageDto.getIsRead())
                .receiver(receiver)
                .sender(sender)
                .organization(organization)
                .build();

        return messageRepository.save(message);
    }

    // message 읽음 처리
    public Message readMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));

        message.markAsRead(true);
        return messageRepository.save(message);
    }

    public MessageResponseDto createMessageResponseDto(Message message) {
        return new MessageResponseDto(
                message.getMessageId(),
                message.getFromNickName(),
                message.getMessageDescription(),
                message.getMessageTime(),
                message.getIsRead(),
                message.getReceiver() != null ? message.getReceiver().getUserId() : null,
                message.getSender() != null ? message.getSender().getUserId() : null,
                message.getOrganization() != null ? message.getOrganization().getOrganizationId() : null,
                message.getOrganization() != null ? message.getOrganization().getOrganizationName() : null
        );
    }

}
