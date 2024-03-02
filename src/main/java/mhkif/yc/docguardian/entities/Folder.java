package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.enums.DocType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Folder {
    @Id
    private UUID uuid;
    @ManyToOne
    private Room room;
    private String name;
    private double size;

    @OneToMany(mappedBy = "folder")
    private List<Document> documents = new ArrayList<>();

}
