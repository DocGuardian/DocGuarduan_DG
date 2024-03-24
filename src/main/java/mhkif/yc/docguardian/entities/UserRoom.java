package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.RoomRoles;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_room")
public class UserRoom {

    @EmbeddedId
    private RoomUsersId id;
    @Enumerated(EnumType.STRING)
    protected RoomRoles permission;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
}
