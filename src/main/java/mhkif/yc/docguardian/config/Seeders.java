package mhkif.yc.docguardian.config;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.Role;
import mhkif.yc.docguardian.repositories.RoomRepository;
import mhkif.yc.docguardian.repositories.RoomUsersRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


@Configuration
@RequiredArgsConstructor
public class Seeders implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Seeders.class);
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomUsersRepository roomUsersRepository;

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
        user.setPassword("aqwzsxedc");
        user.setPhone("0661311145");
        user.setRole(Role.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());

        repository.save(user);
    }

    private void createAdmin(UserRepository repository){

        User user = new User();
        user.setFirst_name("Abdelmalek");
        user.setLast_name("Achkif");
        user.setEmail("malikhkif@gmail.com");
        user.setPassword("aqwzsxedc");
        user.setPhone("0771311145");
        user.setRole(Role.ROLE_ADMIN);
        user.setCreatedAt(LocalDateTime.now());

        repository.save(user);
    }

    private void createRoom(RoomRepository repository, RoomUsersRepository roomUsersRepository){

    }

}
