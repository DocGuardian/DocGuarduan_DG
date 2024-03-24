package mhkif.yc.docguardian.dtos.responses;

import lombok.Data;
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
    private List<UserRes> users = new ArrayList<>();

}
