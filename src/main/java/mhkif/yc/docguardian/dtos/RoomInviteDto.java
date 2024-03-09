package mhkif.yc.docguardian.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RoomInviteDto {
    @NotNull(message = "sender id field is required")
    private UUID senderId;

    @NotNull(message = "recipient id field is required")
    private UUID recipientId;
}
