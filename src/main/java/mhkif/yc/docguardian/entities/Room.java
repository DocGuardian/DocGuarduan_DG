package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.*;

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



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Invitation> invitations;


}
