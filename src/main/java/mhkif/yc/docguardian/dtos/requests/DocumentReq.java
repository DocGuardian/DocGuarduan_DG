package mhkif.yc.docguardian.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.DocType;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentReq {


    @NotNull(message = "sender field is required")
    private User sender;


    @NotNull(message = "room field is required")
    private Room room;


    @NotNull(message = "doc name field is required")
    @NotBlank(message = "doc name field is required")
    private String name;

    @NotNull(message = "doc url field is required")
    @NotBlank(message = "doc url field is required")
    private MultipartFile docUrl;

    @NotNull(message = "doc size field is required")
    @NotBlank(message = "doc size field is required")
    private double size;


    @NotNull(message = "doc type field is required")
    @NotBlank(message = "doc type field is required")
    private DocType type;
}
