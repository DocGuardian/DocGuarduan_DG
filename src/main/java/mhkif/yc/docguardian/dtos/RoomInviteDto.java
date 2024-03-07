package mhkif.yc.docguardian.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RoomInviteDto {
    @NotNull(message = "user id field is required")
    private UUID userId;

    @NotNull(message = "recipient id field is required")
    private UUID recipientId;
}
