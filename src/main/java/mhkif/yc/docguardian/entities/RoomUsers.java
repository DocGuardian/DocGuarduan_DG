package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.Permission;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "room_users")
public class RoomUsers {

    @EmbeddedId
    private RoomUsersId id;
    @Enumerated(EnumType.STRING)
    protected Permission permission;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
}
