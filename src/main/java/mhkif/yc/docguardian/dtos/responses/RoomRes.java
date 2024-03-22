package mhkif.yc.docguardian.dtos.responses;

import lombok.Data;
import mhkif.yc.docguardian.entities.Document;
import mhkif.yc.docguardian.entities.Notification;
import mhkif.yc.docguardian.enums.RoomType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class RoomRes {
    private UUID id;
    private UserRes owner;
    private String name;
    private double storage;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private RoomType type;
    private List<UserRes> users = new ArrayList<>();
    private List<Document> documents = new ArrayList<>();

}
