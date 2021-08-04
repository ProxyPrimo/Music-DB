package primo.musicdb.models.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    private String name;
    private String password;
    private List<UserRoleEntity> roles = new ArrayList<>();


    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }
}
