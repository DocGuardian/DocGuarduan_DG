package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mhkif.yc.docguardian.enums.DocType;

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
    private Room room;
    private String name;
    private double size;
    @Enumerated(EnumType.STRING)
    private DocType type;
}
