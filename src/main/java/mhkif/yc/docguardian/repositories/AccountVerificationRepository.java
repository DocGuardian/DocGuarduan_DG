package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.AccountVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountVerificationRepository extends JpaRepository<AccountVerification, UUID> {
    AccountVerification findByToken(String token);
}
