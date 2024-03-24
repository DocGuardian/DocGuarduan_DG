package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
    Page<Room> findAllByUsersIsContaining(PageRequest pageRequest, User owner);

}
