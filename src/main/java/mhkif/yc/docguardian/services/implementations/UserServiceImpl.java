package mhkif.yc.docguardian.services.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.AccountVerification;
import mhkif.yc.docguardian.entities.Role;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.RoleType;
import mhkif.yc.docguardian.exceptions.BadRequestException;
import mhkif.yc.docguardian.exceptions.EntityAlreadyExistsException;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.AccountVerificationRepository;
import mhkif.yc.docguardian.repositories.RoleRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import mhkif.yc.docguardian.services.EmailService;
import mhkif.yc.docguardian.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final AccountVerificationRepository accountVerificationRepo;
    private final EmailService emailService;
    private  final ModelMapper mapper;

    @Value("${spring.mail.properties.verify.host}")
    private String host;


    @Override
    public UserRes getById(Integer id) {
        return null;
    }

    @Override
    public Page<UserRes> getAllPages(int page, int size) {
        return null;
    }

    @Override
    public List<UserRes> getAll() {
        return null;
    }

    @Override
    public UserRes create(UserReq request) {
        User existingUserEmail = repository.findByEmail(request.getEmail());
        Role user_role = roleRepository.findByName(RoleType.ROLE_USER.name());


        if (Objects.nonNull(existingUserEmail)) {
            throw new EntityAlreadyExistsException("User already exists with the given Email.");
        }
        else if (Objects.isNull(user_role)) {
            throw new NotFoundException("User Role not found");
        }

        User user = mapper.map(request, User.class);
        user.setRole(user_role);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = repository.save(user);

        AccountVerification accountVerification = new AccountVerification();
        accountVerification.setUser(savedUser);

        accountVerificationRepo.save(accountVerification);

        String name = savedUser.getFirst_name()+ " "+savedUser.getLast_name();
        String sendingTo = savedUser.getEmail();
        String url = "users/auth/account-verification/";
        String subject = "DocGuardian : Email Verification ";
        String body = verificationEmailMessage(name, url, this.host,accountVerification.getToken());
        emailService.sendSimpleMailMessage(name, sendingTo, subject, body);

        return mapper.map(savedUser, UserRes.class);
    }

    @Override
    public Boolean verifyToken(String token) throws Exception {
        AccountVerification accountVerification = accountVerificationRepo.findByToken(token);
        if(Objects.isNull(accountVerification)){
            throw  new NotFoundException("Token Not Found ");
        }

        boolean isLessThanHour = this.validateDate(accountVerification.getCreatedAt());

        if (!isLessThanHour && !accountVerification.isVerified()) {
            throw new BadRequestException(("Expired Token : " + token));
        }else if(accountVerification.isVerified()){
            throw new Exception("Your Account is Already verified .");
        }

        User user = repository.findByEmail(accountVerification.getUser().getEmail());
        if (Objects.isNull(user)) {
            throw new NotFoundException("User not found");
        }

        user.setEnabled(true);
        repository.save(user);

        accountVerification.setVerified(true);
        accountVerification.setVerifiedAt(LocalDateTime.now());
        accountVerificationRepo.save(accountVerification);
        return true;
    }

    @Override
    public Boolean sendVerification(String token) throws Exception {
        AccountVerification accountVerification = accountVerificationRepo.findByToken(token);
        if(Objects.isNull(accountVerification)){
            throw  new EntityNotFoundException("Token Not Found ");
        }else if(accountVerification.isVerified()){
            throw new BadRequestException("Your Account is Already verified .");
        }

        User user = repository.findByEmail(accountVerification.getUser().getEmail());
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException("User not found with the given credential.");
        }

        AccountVerification new_confirmation = new AccountVerification();
        new_confirmation.setUser(user);
        accountVerificationRepo.delete(accountVerification); // we have to delete this first before inserting another record in DB
        accountVerificationRepo.save(new_confirmation);

        String name = user.getFirst_name()+ " "+ user.getLast_name();
        String sendingTo = user.getEmail();
        String url = "users/auth/account-verification/";
        String subject = "DocGuardian : Email Verification ";
        String body = verificationEmailMessage(name, url, this.host,new_confirmation.getToken());
        emailService.sendSimpleMailMessage(name, sendingTo, subject, body);

        return true;
    }

    public boolean validateDate(LocalDateTime date) {
        LocalTime sendTime = LocalTime.of(date.getHour(), date.getMinute());
        LocalTime currentTime = LocalTime.of(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
        Duration duration = Duration.between(sendTime, currentTime);
        return duration.toHours() <= 1;
    }

    private String verificationEmailMessage(String name, String url, String host, String token) {
        return "Hello " + name + ", \n\n" +
                "You account has been created successfully," +
                " Please click the link below to verify your account . \n\n" +
                host + url + token + " \n\n" +
                "Note : Link will be expired after 1 hour. \n\n" +
                "The Support Team DocGuardian .";
    }

    @Override
    public UserRes getByEmail(Integer id) {
        return null;
    }

    @Override
    public UserRes getByPhone(Integer id) {
        return null;
    }

    @Override
    public UserRes update(User user) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public User auth(String email, String password) {
        User user = repository.findByEmail(email);
        if(Objects.isNull(user)){
            throw new NotFoundException("No User Found with this Email");
        }
        else if (!Objects.equals(user.getPassword(), password)) {
            throw new BadRequestException("Incorrect Password");
        }

        return user;

    }

    @Override
    public boolean enableAccount(String email) {
        return false;
    }

    @Override
    public boolean lockAccount(String email) {
        return false;
    }

    @Override
    public boolean enableTwoFactorAuth(String email) {
        return false;
    }
}
