package mhkif.yc.docguardian.config;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.entities.Role;
import mhkif.yc.docguardian.enums.RoleType;
import mhkif.yc.docguardian.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class Seeders implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Seeders.class);
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        createRoles(roleRepository);
    }
   private void createRoles(RoleRepository repository){
       Role role_sys_admin = new Role();
       role_sys_admin.setName(RoleType.ROLE_SYSADMIN.name());

       Role role_admin = new Role();
       role_admin.setName(RoleType.ROLE_ADMIN.name());

       Role role_manager = new Role();
       role_manager.setName(RoleType.ROLE_MANAGER.name());

       Role role_user = new Role();
       role_user.setName(RoleType.ROLE_USER.name());

       log.info("Preloading Role: ROLE_SYSADMIN  : " +  repository.save(role_sys_admin));
       log.info("Preloading Role: ROLE_ADMIN  : " +  repository.save(role_admin));
       log.info("Preloading Role: ROLE_MANAGER : " +  repository.save(role_manager));
       log.info("Preloading Role: ROLE_USER  : " +  repository.save(role_user));

   }
}
