package mhkif.yc.docguardian.dtos.requests;

import lombok.Data;
import mhkif.yc.docguardian.entities.Folder;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.RoomType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class RoomReq {
    private User owner;
    private String name;
    //private double storage ;
    private RoomType type;
    //private Set<User> users;
    //private List<Folder> folders;
}
