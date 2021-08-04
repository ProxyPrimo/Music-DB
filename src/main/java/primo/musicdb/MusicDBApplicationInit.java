package primo.musicdb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import primo.musicdb.service.UserRoleService;
import primo.musicdb.service.UserService;

@Component
public class MusicDBApplicationInit implements CommandLineRunner {

    private UserService userService;
    private UserRoleService userRoleService;

    public MusicDBApplicationInit(UserService userService
            , UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.seedRoles();
        userService.seedUsers();
    }
}
