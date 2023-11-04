package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.entity.Role;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.exception.MeetAPIException;
import net.tuke.dt.videoconferenceapi.playload.LoginDTO;
import net.tuke.dt.videoconferenceapi.playload.RegisterDTO;
import net.tuke.dt.videoconferenceapi.repository.ParticipantRepository;
import net.tuke.dt.videoconferenceapi.repository.RoleRepository;
import net.tuke.dt.videoconferenceapi.repository.UserRepository;
import net.tuke.dt.videoconferenceapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {


    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ParticipantRepository participantRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, ParticipantRepository participantRepository,PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.participantRepository = participantRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public String login(LoginDTO loginDto) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User Logged-in success";
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        // check for username in DB
        if (userRepository.existsByEmail(registerDTO.getEmail())){
            throw new MeetAPIException(HttpStatus.BAD_REQUEST,"User already exists");
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setSurname(registerDTO.getSurname());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Participant prt = new Participant();
        prt.setUsername(registerDTO.getUsername());
        user.setParticipant(prt);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();

        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "user registered successfully";
    }
}
