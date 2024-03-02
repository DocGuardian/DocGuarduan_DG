package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.Permission;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_projects")
public class UserProjects {
    @Id
    private UUID uuid;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Enumerated(EnumType.STRING)
    protected Permission permission;

    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;

    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
}
