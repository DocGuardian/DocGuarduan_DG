package mhkif.yc.docguardian.services;

import mhkif.yc.docguardian.dtos.InvitationDto;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.entities.Invitation;

import java.util.UUID;


public interface InvitationService extends Service<Invitation, UUID, InvitationDto, InvitationDto>{
    InvitationDto update(InvitationDto invite);
    boolean delete(UUID id);

}
