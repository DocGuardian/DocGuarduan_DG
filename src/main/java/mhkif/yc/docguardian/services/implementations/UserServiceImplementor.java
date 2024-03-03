package mhkif.yc.docguardian.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.Role;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.RoleType;
import mhkif.yc.docguardian.exceptions.BadRequestException;
import mhkif.yc.docguardian.exceptions.EntityAlreadyExistsException;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.RoleRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import mhkif.yc.docguardian.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementor implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private  final ModelMapper mapper;


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
        return mapper.map(savedUser, UserRes.class);
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
