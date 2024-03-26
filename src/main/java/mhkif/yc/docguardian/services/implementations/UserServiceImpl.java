package mhkif.yc.docguardian.services.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mhkif.yc.docguardian.dtos.InvitationDto;
import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.AccountVerification;
import mhkif.yc.docguardian.entities.ResetPasswordVerification;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.Role;
import mhkif.yc.docguardian.exceptions.BadRequestException;
import mhkif.yc.docguardian.exceptions.EntityAlreadyExistsException;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.AccountVerificationRepository;
import mhkif.yc.docguardian.repositories.InvitationRepository;
import mhkif.yc.docguardian.repositories.ResetPasswordVerificationRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import mhkif.yc.docguardian.services.EmailService;
import mhkif.yc.docguardian.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final AccountVerificationRepository accountVerificationRepo;
    private final ResetPasswordVerificationRepository resetPasswordRepo;
    private final EmailService emailService;
    private final InvitationRepository invitationRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

    @Value("${spring.mail.properties.verify.host}")
    private String host;
    private String sendingBy = "abdelmalekachkif@gmail.com";



    @Override
    public UserRes getById(UUID id) {
        Optional<User> userOp = repository.findById(id);
        return userOp.map(
                user -> mapper.map(user, UserRes.class)
        ).orElseThrow(() -> new NotFoundException("User Not Exist with the given Id : " + id)
        );
    }

    @Override
    public Page<UserRes> getAllPages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(
                user -> mapper.map(user, UserRes.class)
        );
    }

    @Override
    public List<UserRes> getAll() {
        return repository.findAll().stream().map(
                user -> mapper.map(user, UserRes.class)
        ).toList();
    }

    @Override
    public UserRes create(UserReq request) {
        User existingUserEmail = repository.findByEmail(request.getEmail());

        if (Objects.nonNull(existingUserEmail)) {
            throw new EntityAlreadyExistsException("User already exists with the given Email.");
        }

        User user = mapper.map(request, User.class);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = repository.save(user);

        AccountVerification accountVerification = new AccountVerification();
        accountVerification.setUser(savedUser);

        accountVerificationRepo.save(accountVerification);

        String name = savedUser.getFirst_name()+ " "+savedUser.getLast_name();
        String sendingTo = savedUser.getEmail();
        String url = "auth/account-verification/";
        String subject = "DocGuardian : Email Verification ";
        String body = verificationEmailMessage(name, url, this.host,accountVerification.getToken());
        emailService.sendSimpleMailMessage(name, this.sendingBy ,sendingTo, subject, body);

        return mapper.map(savedUser, UserRes.class);
    }

    @Override
    public boolean verifyToken(String token) throws Exception {
        AccountVerification accountVerification = accountVerificationRepo.findByToken(token);
        if(Objects.isNull(accountVerification)){
            throw  new NotFoundException("Token Not Found ");
        }

        boolean isLessThanHour = this.validateDate(accountVerification.getCreatedAt(), 1);

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
    public boolean sendVerification(String token) throws Exception {
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
        String url = "auth/account/verification/";
        String subject = "DocGuardian : Email Verification ";
        String body = verificationEmailMessage(name, url, this.host,new_confirmation.getToken());
        emailService.sendSimpleMailMessage(name,this.sendingBy, sendingTo, subject, body);

        return true;
    }

    @Override
    public void sendResetPassword(String email) {
        User user = repository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException("User not found with the given credential.");
        }

        ResetPasswordVerification resetPassword = new ResetPasswordVerification();
        resetPassword.setUser(user);
        resetPassword.setExpiredAt(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(2)));
        resetPasswordRepo.save(resetPassword);

        String name = user.getFirst_name()+ " "+ user.getLast_name();
        String sendingTo = user.getEmail();
        String url = "users/auth/account/reset-password/";
        String subject = "DocGuardian : Reset Password ";
        String body = resetPasswordMessage(name, url, this.host,resetPassword.getToken());
        emailService.sendSimpleMailMessage(name, this.sendingBy, sendingTo, subject, body);

    }

    @Override
    public void resetPassword(String token, String new_password) {
        ResetPasswordVerification resetPassword = resetPasswordRepo.findByToken(token);
        if(Objects.isNull(resetPassword)){
            throw  new NotFoundException("Token Not Found ");
        }

        boolean isLessThan2Hour = this.validateDate(resetPassword.getCreatedAt(), 2);

        if (!isLessThan2Hour ||  Objects.nonNull(resetPassword.getVerifiedAt())) {
            throw new BadRequestException(("Expired Token : " + token));
        }

        User user = repository.findByEmail(resetPassword.getUser().getEmail());
        if (Objects.isNull(user)) {
            throw new NotFoundException("User not found");
        }

        user.setPassword(new_password);
        repository.save(user);

        resetPassword.setVerifiedAt(LocalDateTime.now());
        resetPasswordRepo.save(resetPassword);

    }

    public boolean validateDate(LocalDateTime date, int period) {
        LocalTime sendTime = LocalTime.of(date.getHour(), date.getMinute());
        LocalTime currentTime = LocalTime.of(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
        Duration duration = Duration.between(sendTime, currentTime);
        return duration.toHours() <= period;
    }


    @Override
    public UserRes getByEmail(String email) {
        User user = repository.findByEmail(email);
        if(Objects.isNull(user)){
            throw new NotFoundException("User Not Exist with the given Email : " + email);
        }
        return mapper.map(user, UserRes.class);

    }

    @Override
    public UserRes getByPhone(String phone) {
        return null;
    }

    @Override
    public UserRes update(User user) {
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    @Override
    public User auth(String email, String password) {
        User user = repository.findByEmail(email);
        if(Objects.isNull(user)){
            throw new NotFoundException("No User Found with this Email");
        }
        else if (!encoder.matches(password, user.getPassword())) {
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

    @Override
    public List<InvitationDto> getInvitations(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with the given credential.")
                );

        return invitationRepository.findAllByRecipient(user)
                .stream().map(invitation -> mapper.map(invitation, InvitationDto.class))
                .toList();
    }

    public List<InvitationDto> getNotifications(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with the given credential.")
                );

        return invitationRepository.findAllByRecipient(user)
                .stream().map(invitation -> mapper.map(invitation, InvitationDto.class))
                .toList();
    }

    private String verificationEmailMessage(String name, String url, String host, String token) {
        return "Hello " + name + ", \n\n" +
                "You account has been created successfully," +
                " Please click the link below to verify your account . \n\n" +
                host + url + token + " \n\n" +
                "Note : Link will be expired after 1 hour. \n\n" +
                "The Support Team DocGuardian .";
    }
    private String resetPasswordMessage(String name, String url, String host, String token) {
        return "Hello " + name + ", \n\n" +
                "You are about to reset your password," +
                " Please click the link below to update your password . \n\n" +
                host + url + token + " \n\n" +
                "Note : Link will be expired after 2 hour. \n\n" +
                "The Support Team DocGuardian .";
    }

}
