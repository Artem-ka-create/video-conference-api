package net.tuke.dt.videoconferenceapi.security;

import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this. userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User usr = userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User not found with username or email" + email));
        Set<GrantedAuthority> authoritySet = usr.getRoles().stream().map( (role) ->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());


        return new org.springframework.security.core.userdetails.User(
                        usr.getEmail(),
                        usr.getPassword(),
                        authoritySet);
    }
}
