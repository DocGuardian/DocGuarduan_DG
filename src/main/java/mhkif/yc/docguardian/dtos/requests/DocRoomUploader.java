package mhkif.yc.docguardian.dtos.requests;

import lombok.Data;
import mhkif.yc.docguardian.entities.Room;

@Data
public class DocRoomUploader {
    private Room room;
}
