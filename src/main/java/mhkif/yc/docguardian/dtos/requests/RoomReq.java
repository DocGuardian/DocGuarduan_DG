package mhkif.yc.docguardian.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mhkif.yc.docguardian.entities.User;

@Data
public class RoomReq {
    @NotNull(message = "User cannot be null")
    private User owner;

    @NotNull(message = "doc name field is required")
    @NotBlank(message = "doc name field is required")
    private String name;
}
