package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.*;
import mhkif.yc.docguardian.enums.RoomType;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double storage;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "room_users",
            joinColumns = {
                    @JoinColumn(name = "room_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id")
            }
    )
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Folder> folders = new ArrayList<>();


}
