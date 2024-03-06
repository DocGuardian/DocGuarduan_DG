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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String first_name;
    private String last_name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(nullable = false, unique = true)
    private String phone;
    private String imageUrl;
    @Column(columnDefinition = "boolean default false")
    private boolean isEnabled;
    @Column(columnDefinition = "boolean default false")
    private boolean isLocked;
    @Column(columnDefinition = "boolean default false")
    private boolean enable_tfa;
    @OneToOne()
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @ManyToMany(cascade = CascadeType.ALL,
            mappedBy = "users"
    )
    private Set<Room> rooms = new HashSet<>();

}
