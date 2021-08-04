package primo.musicdb.service;

import primo.musicdb.model.service.UserRegistrationServiceModel;

public interface UserService {
    void seedUsers();
    void registerAndLoginUser(UserRegistrationServiceModel serviceModel);

    boolean userNameExists(String username);
    boolean emailExists(String email);
}
