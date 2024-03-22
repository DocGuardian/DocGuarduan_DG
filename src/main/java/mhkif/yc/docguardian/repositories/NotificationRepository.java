package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByRecipientIdOrderByTimestampDesc(UUID userId);
}
