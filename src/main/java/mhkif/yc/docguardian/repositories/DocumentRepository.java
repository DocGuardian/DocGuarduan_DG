package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.Document;
import mhkif.yc.docguardian.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    Page<Document> findAllBySender(PageRequest pageRequest, User sender);
}
