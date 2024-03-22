package mhkif.yc.docguardian.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NotificationDto {

    private UUID id;

    @NotNull(message = "Sender field is required")
    @NotBlank(message = "Sender field is required")
    private UserRes sender;

    @NotNull(message = "Recipient field is required")
    @NotBlank(message = "Recipient field is required")
    private UserRes recipient;

    @NotNull(message = "message field is required")
    @NotBlank(message = "message field is required")
    private RoomRes room;

    @NotNull(message = "message field is required")
    @NotBlank(message = "message field is required")
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    @NotNull(message = "notification type field is required")
    @NotBlank(message = "notification type is required")
    private NotificationType type;
    private boolean isRead;


}
