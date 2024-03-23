package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

//@Entity
@Table(name = "user_activities")
@Data
public class UserActivities {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    //private User user;
    //private Activity activity;
    private String device;
    private String ip_address;
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
