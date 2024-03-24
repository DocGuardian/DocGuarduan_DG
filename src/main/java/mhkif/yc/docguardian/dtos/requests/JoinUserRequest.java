package mhkif.yc.docguardian.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mhkif.yc.docguardian.enums.RoomRoles;

import java.util.UUID;

@Getter @Setter
public class JoinUserRequest {
    @NotNull(message = "Notif ID cannot be null")
    private UUID notifId;

    @NotNull(message = "Room ID cannot be null")
    private UUID roomId;

    @NotNull(message = "User ID cannot be null")
    private UUID userId;


}