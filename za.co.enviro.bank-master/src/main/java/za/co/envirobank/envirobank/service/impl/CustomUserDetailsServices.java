package za.co.envirobank.envirobank.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.envirobank.envirobank.model.entities.UserEntity;
import za.co.envirobank.envirobank.service.UserService;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServices implements UserDetailsService {
    private  final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        UserEntity user = userService
                .findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(
                        () -> new EntityNotFoundException(
                "Could not find user with username" + usernameOrEmail));
        Set<GrantedAuthority> authorities = user
                .getRole()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

//        UserEntity user = userService.findByEmail(username).orElseThrow(() -> new EntityNotFoundException(
//                "Could not find user with username" + username));

        return new org.springframework.security.core.userdetails.User(userService.getUserEmailBy(user.getUsername()),user.getPassword(),
                authorities);

//        return new org.springframework.security.core.userdetails.User(user.getUsername()),user.getPassword(),
//                authorities);
    }
}
