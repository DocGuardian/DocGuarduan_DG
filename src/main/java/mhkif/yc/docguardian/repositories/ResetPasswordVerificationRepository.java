package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.ResetPasswordVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResetPasswordVerificationRepository extends JpaRepository<ResetPasswordVerification, UUID> {
    ResetPasswordVerification findByToken(String token);
}
