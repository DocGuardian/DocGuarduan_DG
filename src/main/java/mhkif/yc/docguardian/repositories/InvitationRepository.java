package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.Invitation;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
    List<Invitation> findAllByRecipient(User user);
    Invitation findByCreatedAtAndRecipientAndSenderAndRoom(LocalDateTime dateTime, User recipient, User sender, Room room);
}
