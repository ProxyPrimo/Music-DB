package primo.musicdb.models.entities;

import primo.musicdb.models.entities.enums.UserRoles;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {
    private UserRoles role;

    @Enumerated
    public UserRoles getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoles role) {
        this.role = role;
        return this;
    }
}
