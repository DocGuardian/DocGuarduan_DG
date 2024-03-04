package mhkif.yc.docguardian.services;

import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.services.Service;

public interface UserService extends Service<User, Integer, UserReq, UserRes> {

    boolean verifyToken(String token) throws Exception;

    boolean sendVerification(String token) throws Exception;

    void sendResetPassword(String email);

    void resetPassword(String token,  String new_password);

    UserRes getByEmail(Integer id);
    UserRes getByPhone(Integer id);
    UserRes update(User user);
    boolean delete(Integer id);
    User auth(String email, String password);
    boolean enableAccount(String email);
    boolean lockAccount(String email);
    boolean enableTwoFactorAuth(String email);

}
