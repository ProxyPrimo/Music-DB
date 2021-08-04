package primo.musicdb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import primo.musicdb.model.entities.UserRoleEntity;
import primo.musicdb.model.entities.enums.UserRole;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByRole(UserRole role);
}
