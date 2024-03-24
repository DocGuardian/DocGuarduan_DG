package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.Document;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    Page<Document> findAllBySender(PageRequest pageRequest, User sender);
    @Query("SELECT d FROM Document d " +
            "JOIN d.room r " +
            "JOIN r.users u " +
            "WHERE u.id = :userId")
    Page<Document> findDocumentsForUserRooms(PageRequest pageRequest, @Param("userId") UUID userId);

    List<Document> findAllByRoom(Room room);
}
