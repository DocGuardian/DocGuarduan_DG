package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.ActivityType;
import mhkif.yc.docguardian.enums.RoomActivityType;

import java.util.UUID;

@Entity
@Table(name = "room_activities")
@Data
public class RoomActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoomActivityType type;
    private String description;
}
