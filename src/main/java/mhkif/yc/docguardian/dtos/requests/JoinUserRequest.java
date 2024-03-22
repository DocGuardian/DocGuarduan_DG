package mhkif.yc.docguardian.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class JoinUserRequest {

    @NotNull(message = "Room ID cannot be null")
    private UUID roomId;

    @NotNull(message = "User ID cannot be null")
    private UUID userId;

}