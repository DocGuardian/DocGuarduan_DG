package mhkif.yc.docguardian.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import mhkif.yc.docguardian.enums.NotificationType;

@Entity
@Getter @Setter
public class MessageNotification extends Notification{
    @ManyToOne
    private Room room;

    public MessageNotification() {
        super.setType(NotificationType.MESSAGE);
    }
}
