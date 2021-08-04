package primo.musicdb.service.impl;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import primo.musicdb.model.entities.UserEntity;
import primo.musicdb.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicDBUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public MusicDBUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByName(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Invalid username")
                );

        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(UserEntity user) {
        List<GrantedAuthority> authorities =
                user
                        .getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority(r.getRole().name()))
                        .collect(Collectors.toList());

        return new User(
                user.getName(),
                user.getPassword(),
                authorities
        );
    }
}
