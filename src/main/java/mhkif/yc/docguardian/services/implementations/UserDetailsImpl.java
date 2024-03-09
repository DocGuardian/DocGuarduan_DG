package mhkif.yc.docguardian.services.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mhkif.yc.docguardian.config.security.authenticators.AuthenticatedUser;
import mhkif.yc.docguardian.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = userRepo.findByEmail(email);

        if(user != null){
            AuthenticatedUser authUser = new AuthenticatedUser(user);

            log.info("User Email: "+user.getEmail());
            log.info("User Permissions : "+user.getRole().getPermissions());
            log.info("User Authorities : "+user.getRole().getAuthorities());
            log.info("AuthenticatedUser Authorities : "+authUser.getAuthorities());
            return authUser;
        }else{
            throw new UsernameNotFoundException("User with email not found : "+email);
        }

    }
}