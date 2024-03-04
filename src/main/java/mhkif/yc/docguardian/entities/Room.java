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



    @OneToMany(mappedBy = "room")
    private List<Folder> folders = new ArrayList<>();

}
