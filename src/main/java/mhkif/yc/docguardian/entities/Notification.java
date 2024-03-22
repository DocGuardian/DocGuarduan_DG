package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mhkif.yc.docguardian.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User recipient;
    private String message;
    private LocalDateTime timestamp;
    private NotificationType type;
    private boolean isRead = false;

}