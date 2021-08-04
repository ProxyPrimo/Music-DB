package primo.musicdb.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import primo.musicdb.model.entities.UserEntity;
import primo.musicdb.model.entities.UserRoleEntity;
import primo.musicdb.model.entities.enums.UserRole;
import primo.musicdb.model.service.UserRegistrationServiceModel;
import primo.musicdb.repository.UserRepository;
import primo.musicdb.service.UserRoleService;
import primo.musicdb.service.UserService;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRoleService userRoleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MusicDBUserService musicDBUserService;

    public UserServiceImpl(UserRoleService userRoleService, UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, MusicDBUserService musicDBUserService) {
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.musicDBUserService = musicDBUserService;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin
                    .setUsername("admin")
                    .setFullName("Admin Admin")
                    .setEmail("admin@admin.com")
                    .setPassword(passwordEncoder.encode("admin123"));

            UserEntity user = new UserEntity();
            user
                    .setUsername("user")
                    .setFullName("User User")
                    .setEmail("user@user.com")
                    .setPassword(passwordEncoder.encode("user123"));

            UserRoleEntity userRole = userRoleService.findByRole(UserRole.USER);
            UserRoleEntity adminRole = userRoleService.findByRole(UserRole.ADMIN);

            admin.setRoles(List.of(adminRole, userRole));
            user.setRoles(List.of(userRole));

            userRepository.saveAll(List.of(admin, user));
        }
    }

    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel serviceModel) {
        UserEntity userEntity = modelMapper.map(serviceModel, UserEntity.class);

        UserRoleEntity userRoleEntity =
                userRoleService
                        .findByRole(UserRole.USER);

        userEntity.addRole(userRoleEntity);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.saveAndFlush(userEntity);

        UserDetails userDetails = musicDBUserService
                .loadUserByUsername(userEntity.getUsername());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, userEntity.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }
}
