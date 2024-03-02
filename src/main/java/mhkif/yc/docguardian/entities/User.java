package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    private UUID uuid;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String phone;
    private String imageUrl;
    private boolean isEnabled;
    private boolean isLocked;
    private boolean is_tfa_enabled;
    @OneToOne()
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "owner")
    private Set<Room> rooms = new HashSet<>();
}
