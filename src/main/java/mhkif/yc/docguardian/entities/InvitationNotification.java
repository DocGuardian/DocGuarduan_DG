package mhkif.yc.docguardian.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import mhkif.yc.docguardian.enums.NotificationType;

@Entity
@Getter
@Setter
public class InvitationNotification extends Notification {

    @ManyToOne
    private Room room;

    public InvitationNotification() {
        super.setType(NotificationType.INVITATION);
    }


}

