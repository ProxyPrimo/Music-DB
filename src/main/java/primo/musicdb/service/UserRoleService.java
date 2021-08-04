package primo.musicdb.service;

import primo.musicdb.model.entities.UserRoleEntity;
import primo.musicdb.model.entities.enums.UserRole;

public interface UserRoleService {
    void seedRoles();
    UserRoleEntity findByRole(UserRole role);
}
