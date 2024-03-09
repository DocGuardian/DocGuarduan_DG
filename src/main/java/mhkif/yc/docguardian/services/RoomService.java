package mhkif.yc.docguardian.services;

import mhkif.yc.docguardian.dtos.requests.RoomReq;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.Room;

import java.util.UUID;

public interface RoomService extends Service<Room, UUID, RoomReq, RoomRes>{

    RoomRes update(RoomRes room);
    RoomRes inviteUserViaEmail(UUID id, UUID UserId, String recipientEmail);

    RoomRes inviteUser(UUID id, UUID userId, UUID recipientId);
}
