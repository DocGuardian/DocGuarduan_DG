package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.*;
import mhkif.yc.docguardian.enums.Role;

import java.time.LocalDateTime;
import java.util.*;

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
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @ManyToMany(cascade = CascadeType.ALL,
            mappedBy = "users"
    )
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<Invitation> receivedInvitations;

}
