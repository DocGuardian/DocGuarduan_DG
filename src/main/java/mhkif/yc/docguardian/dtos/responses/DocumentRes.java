package mhkif.yc.docguardian.dtos.responses;

import lombok.Data;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.DocType;

import java.util.UUID;

@Data
public class DocumentRes {

    private UUID id;
    private User sender;
    private Room room;
    private String name;
    private String docUrl;
    private double size;
    private DocType type;
}
