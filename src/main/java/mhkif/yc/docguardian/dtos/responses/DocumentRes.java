package mhkif.yc.docguardian.dtos.responses;

import lombok.Data;
import mhkif.yc.docguardian.enums.DocType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DocumentRes {

    private UUID id;
    private UserRes sender;
    private RoomRes room;
    private String name;
    private String docUrl;
    private double size;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
