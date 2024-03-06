package mhkif.yc.docguardian.dtos.responses;

import jakarta.persistence.*;
import lombok.Data;
import mhkif.yc.docguardian.entities.Folder;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.RoomType;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class RoomRes {
    private UUID id;
    private User owner;
    private String name;
    private double storage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private RoomType type;
    private List<User> users;
    private List<Folder> folders;

}
