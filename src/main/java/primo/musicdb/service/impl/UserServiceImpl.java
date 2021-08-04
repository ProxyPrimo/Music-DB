package primo.musicdb.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import primo.musicdb.model.entities.UserEntity;
import primo.musicdb.model.entities.UserRoleEntity;
import primo.musicdb.model.entities.enums.UserRole;
import primo.musicdb.repository.UserRepository;
import primo.musicdb.service.UserRoleService;
import primo.musicdb.service.UserService;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRoleService userRoleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRoleService userRoleService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));

            UserEntity user = new UserEntity();
            user.setName("user");
            user.setPassword(passwordEncoder.encode("user123"));

            UserRoleEntity userRole = userRoleService.findByRole(UserRole.USER);
            UserRoleEntity adminRole = userRoleService.findByRole(UserRole.ADMIN);

            admin.setRoles(List.of(adminRole, userRole));
            user.setRoles(List.of(userRole));

            userRepository.saveAll(List.of(admin, user));
        }
    }
}
