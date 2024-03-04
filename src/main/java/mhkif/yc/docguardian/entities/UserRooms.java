package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.Permission;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class UserRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Enumerated(EnumType.STRING)
    protected Permission permission;

    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;

    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
}
