package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

//@Entity
@Data
public class RoomUsersActivities {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    //private Room room
    //private user user;
    //private RoomActivity activity;
    //private String device;
    //private String ip_address;
    //@Column(nullable = false)
   // private LocalDateTime createdAt;

}
