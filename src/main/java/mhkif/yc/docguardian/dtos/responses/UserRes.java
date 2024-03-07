package mhkif.yc.docguardian.dtos.responses;

import lombok.Data;
import mhkif.yc.docguardian.enums.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class UserRes {

    private UUID id;
    private String first_name;
    private String last_name;
    private String email;
    // TODO : Password should not be retrieved
    private String password;
    private String phone;
    private String imageUrl;
    private boolean isEnabled;
    private boolean isLocked;
    private boolean enable_tfa;
    private Role role;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
