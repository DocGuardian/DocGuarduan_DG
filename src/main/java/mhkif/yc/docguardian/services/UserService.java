package mhkif.yc.docguardian.services;

import mhkif.yc.docguardian.dtos.InvitationDto;
import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.services.Service;

import java.util.List;
import java.util.UUID;

public interface UserService extends Service<User, UUID, UserReq, UserRes> {

    boolean verifyToken(String token) throws Exception;

    boolean sendVerification(String token) throws Exception;

    void sendResetPassword(String email);

    void resetPassword(String token,  String new_password);

    UserRes getByEmail(String id);
    UserRes getByPhone(String id);
    UserRes update(User user);
    boolean delete(UUID id);
    User auth(String email, String password);
    boolean enableAccount(String email);
    boolean lockAccount(String email);
    boolean enableTwoFactorAuth(String email);

    List<InvitationDto> getInvitations(UUID id);
}
