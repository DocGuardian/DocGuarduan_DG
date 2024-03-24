package mhkif.yc.docguardian.services;

import mhkif.yc.docguardian.dtos.requests.RoomReq;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.UserRoom;
import mhkif.yc.docguardian.enums.RoomRoles;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface RoomService extends Service<Room, UUID, RoomReq, RoomRes>{

    UserRoom checkUserPermission(UUID id, UUID userId);

    RoomRes update(RoomRes room);



    RoomRes joinUser(UUID notifId, UUID id, UUID userId);

    RoomRes inviteUserViaEmail(UUID id, UUID UserId, String recipientEmail);

    RoomRes inviteUser(UUID id, UUID userId, UUID recipientId);

    Page<RoomRes> getPagesByUser(int page, int size, UUID userId);

    boolean leaveUser(UUID id, UUID userId);
}
