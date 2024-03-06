package mhkif.yc.docguardian.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomUsersId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @MapsId("user_id")
    private UUID user_id;
    @MapsId("room_id")
    private UUID room_id;
}

