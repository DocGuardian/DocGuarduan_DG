package mhkif.yc.docguardian.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RoomInviteByEmailDto {
    @NotNull(message = "user id field is required")
    private UUID userId;

    @NotNull(message = "email field is required")
    @NotBlank(message = "email field is required")
    @Email(message = "this field must be a valid email")
    private String email;
}
