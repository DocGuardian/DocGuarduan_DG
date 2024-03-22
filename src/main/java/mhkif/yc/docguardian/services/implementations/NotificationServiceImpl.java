package mhkif.yc.docguardian.services.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.NotificationDto;
import mhkif.yc.docguardian.entities.*;
import mhkif.yc.docguardian.enums.InvitationStatus;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.InvitationRepository;
import mhkif.yc.docguardian.repositories.NotificationRepository;
import mhkif.yc.docguardian.repositories.RoomRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import mhkif.yc.docguardian.services.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static mhkif.yc.docguardian.enums.InvitationStatus.ACCEPTED;
import static mhkif.yc.docguardian.enums.InvitationStatus.REFUSED;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final InvitationRepository invitationRepository;

    private final ModelMapper mapper;
    @Override
    public Notification sendNotification(UUID recipientId, UUID senderId, String message) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new NotFoundException("Sender not found"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new NotFoundException("Recipient not found"));

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setMessage(message);

        return  repository.save(notification);
    }

    @Override
    public InvitationNotification sendInvitation(UUID recipientId, UUID senderId, UUID roomId, String message) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new NotFoundException("Sender not found"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new NotFoundException("Recipient not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room not found"));

        InvitationNotification notification = new InvitationNotification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setRoom(room);
        notification.setMessage(message);

       return  repository.save(notification);
    }

    @Override
    public MessageNotification sendMessage(UUID recipientId, UUID senderId, UUID roomId, String message) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new NotFoundException("Sender not found"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new NotFoundException("Recipient not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room not found"));

        MessageNotification notification = new MessageNotification();
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notification.setRoom(room);
        notification.setMessage(message);

        return  repository.save(notification);
    }

    @Override
    public List<NotificationDto> getNotificationsForUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with the given credential.")
                );

        return repository.findByRecipientIdOrderByTimestampDesc(user.getId())
                .stream().map(
                        notification -> mapper.map(notification, NotificationDto.class)
                ).toList();
    }

    @Override
    public void markNotificationAsRead(UUID notificationId) {
        Notification notification = repository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            repository.save(notification);
        }
    }

    @Override
    public InvitationNotification updateInvitation(InvitationNotification invitationNotification) {
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        Notification notification = repository.findById(id).orElseThrow(() -> new NotFoundException("Notification not found"));
        Invitation invitation = notification.getRecipient().getReceivedInvitations().stream()
                .filter(inv -> inv.getRoom().getId().equals(id)).findFirst().orElseThrow(
                        () ->   new NotFoundException("Invitation not found")
                );
        invitation.setStatus(REFUSED);
        invitationRepository.save(invitation);
        repository.delete(notification);

        return repository.existsById(id);
    }
}
