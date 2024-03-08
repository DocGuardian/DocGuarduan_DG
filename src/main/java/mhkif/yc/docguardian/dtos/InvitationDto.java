package mhkif.yc.docguardian.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.enums.InvitationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class InvitationDto {
    private UUID id;
    @NotNull(message = "sender id field is required")
    private UserRes sender;
    @NotNull(message = "recipient id field is required")
    private UserRes recipient;
    @NotNull(message = "room id field is required")
    private RoomRes room;
    private InvitationStatus status = InvitationStatus.WAITING;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
