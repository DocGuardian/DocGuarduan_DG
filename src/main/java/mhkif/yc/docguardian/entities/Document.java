package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mhkif.yc.docguardian.enums.DocType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private Room room;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String docUrl;
    @Column(nullable = false)
    private double size;
    //@Enumerated(EnumType.STRING)
    private String type;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
}
