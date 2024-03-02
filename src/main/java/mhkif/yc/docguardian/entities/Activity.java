package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.ActivityType;

@Entity
@Table(name = "activities")
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private ActivityType type;
    private String description;

}
