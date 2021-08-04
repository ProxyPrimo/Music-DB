package primo.musicdb.service.impl;

import org.springframework.stereotype.Service;
import primo.musicdb.model.entities.UserRoleEntity;
import primo.musicdb.model.entities.enums.UserRole;
import primo.musicdb.repository.UserRoleRepository;
import primo.musicdb.service.UserRoleService;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void seedRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));
        }
    }

    @Override
    public UserRoleEntity findByRole(UserRole role) {
        return userRoleRepository.findByRole(role).orElse(null);
    }
}
