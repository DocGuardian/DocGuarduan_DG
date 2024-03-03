package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RoomUsersActivities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //private Room room
    //private user user;
    //private RoomActivity activity;
    private String device;
    private String ip_address;
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
