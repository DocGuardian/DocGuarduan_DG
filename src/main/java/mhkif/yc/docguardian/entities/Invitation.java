package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.InvitationStatus;
import mhkif.yc.docguardian.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User recipient;
    @ManyToOne
    private Room room;
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
