package mhkif.yc.docguardian.repositories;

import mhkif.yc.docguardian.entities.UserRoom;
import mhkif.yc.docguardian.entities.RoomUsersId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomUsersRepository extends JpaRepository<UserRoom, RoomUsersId> {
}
