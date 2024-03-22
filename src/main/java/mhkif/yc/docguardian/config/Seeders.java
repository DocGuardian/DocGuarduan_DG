package mhkif.yc.docguardian.config;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.entities.User;
import static mhkif.yc.docguardian.enums.Role.ADMIN;
import static mhkif.yc.docguardian.enums.Role.USER;
import mhkif.yc.docguardian.repositories.RoomRepository;
import mhkif.yc.docguardian.repositories.RoomUsersRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class Seeders implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Seeders.class);
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomUsersRepository roomUsersRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {

        createAdmin(userRepository);
        createUser(userRepository);
        createRoom(roomRepository, roomUsersRepository);
    }

    private void createUser(UserRepository repository){
        User user = new User();
        user.setFirst_name("Aziz");
        user.setLast_name("harkati");
        user.setEmail("aziz@gmail.com");
        user.setPassword(encoder.encode("aqwzsxedc"));
        user.setPhone("0661311145");
        user.setRole(USER);
        user.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setFirst_name("Mohammed");
        user2.setLast_name("Achkif");
        user2.setEmail("medachkif@gmail.com");
        user2.setPassword(encoder.encode("aqwzsxedc"));
        user2.setPhone("0781311145");
        user2.setRole(USER);
        user2.setCreatedAt(LocalDateTime.now());

        User user3 = new User();
        user3.setFirst_name("Ahmed");
        user3.setLast_name("RokenEddine");
        user3.setEmail("ahmed@gmail.com");
        user3.setPassword(encoder.encode("aqwzsxedc"));
        user3.setPhone("0791311145");
        user3.setRole(USER);
        user3.setCreatedAt(LocalDateTime.now());

        List<User> users = List.of(user, user2, user3);
        repository.saveAll(users);
    }

    private void createAdmin(UserRepository repository){

        User user = new User();
        user.setFirst_name("Abdelmalek");
        user.setLast_name("Achkif");
        user.setEmail("malikhkif@gmail.com");
        user.setPassword(encoder.encode("aqwzsxedc"));
        user.setPhone("0771311145");
        user.setRole(ADMIN);
        user.setCreatedAt(LocalDateTime.now());

        repository.save(user);
    }

    private void createRoom(RoomRepository repository, RoomUsersRepository roomUsersRepository){

    }

}
