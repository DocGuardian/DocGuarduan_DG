package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
}
