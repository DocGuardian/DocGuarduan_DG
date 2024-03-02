package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activities")
@Data
public class UserActivities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //private User user;
    //private Activity activity;
    private String device;
    private String ip_address;
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
