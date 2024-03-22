package mhkif.yc.docguardian.services;

import mhkif.yc.docguardian.dtos.InvitationDto;
import mhkif.yc.docguardian.dtos.NotificationDto;
import mhkif.yc.docguardian.entities.InvitationNotification;
import mhkif.yc.docguardian.entities.MessageNotification;
import mhkif.yc.docguardian.entities.Notification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    Notification sendNotification(UUID recipientId, UUID senderId, String message);
    InvitationNotification sendInvitation(UUID recipientId, UUID senderId, UUID roomId, String message);


    MessageNotification sendMessage(UUID recipientId, UUID senderId, UUID roomId, String message);


    List<NotificationDto> getNotificationsForUser(UUID userId);

    void markNotificationAsRead(UUID notificationId);
    InvitationNotification updateInvitation(InvitationNotification invitationNotification);
    boolean delete(UUID id);


}
