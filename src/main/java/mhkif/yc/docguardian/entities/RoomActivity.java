package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.ActivityType;
import mhkif.yc.docguardian.enums.RoomActivityType;

@Entity
@Table(name = "room_activities")
@Data
public class RoomActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoomActivityType type;
    private String description;
}
