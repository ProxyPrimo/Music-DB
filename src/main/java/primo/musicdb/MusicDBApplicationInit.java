package primo.musicdb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import primo.musicdb.service.ArtistService;
import primo.musicdb.service.UserRoleService;
import primo.musicdb.service.UserService;

@Component
public class MusicDBApplicationInit implements CommandLineRunner {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ArtistService artistService;

    public MusicDBApplicationInit(UserService userService
            , UserRoleService userRoleService, ArtistService artistService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.artistService = artistService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.seedRoles();
        userService.seedUsers();

        artistService.seedArtists();
    }
}
