package za.co.envirobank.envirobank.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.UserEntity;
import za.co.envirobank.envirobank.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServicesImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        UserEntity user = null;
        user = userService
                .findByUsernameOrEmail(usernameOrEmail).orElseThrow(
                        () -> new EntityNotFoundException(
                                "Could not find user with username" + usernameOrEmail));


        Set<GrantedAuthority> authorities = user
                .getRole()
                .stream()
                .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toSet());


        return new org.springframework.security.core.userdetails.User(userService.getUserEmailBy(user.getUsername()), user.getPassword(),
                authorities);

    }
}
