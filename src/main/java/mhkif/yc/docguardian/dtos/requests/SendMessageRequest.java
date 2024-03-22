package mhkif.yc.docguardian.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.User;

@Getter @Setter
public class SendMessageRequest {
    @NotNull(message = "Sender field is required")
    @NotBlank(message = "Sender field is required")
    private User sender;

    @NotNull(message = "Recipient field is required")
    @NotBlank(message = "Recipient field is required")
    private User recipient;

    @NotNull(message = "room id field is required")
    @NotBlank(message = "room id field is required")
    private Room room;

    @NotNull(message = "message field is required")
    @NotBlank(message = "message field is required")
    private String message;
}
