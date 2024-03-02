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
    private UUID uuid;
    @ManyToOne
    private Folder folder;
    private String name;
    private double size;
    @Enumerated(EnumType.STRING)
    private DocType type;
}
