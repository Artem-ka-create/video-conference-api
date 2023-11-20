package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.entity.Role;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.exception.MeetAPIException;
import net.tuke.dt.videoconferenceapi.exception.ResourceNotFoundException;
import net.tuke.dt.videoconferenceapi.playload.JWTAuthResponse;
import net.tuke.dt.videoconferenceapi.playload.LoginDTO;
import net.tuke.dt.videoconferenceapi.playload.LoginResponse;
import net.tuke.dt.videoconferenceapi.playload.RegisterDTO;
import net.tuke.dt.videoconferenceapi.repository.ParticipantRepository;
import net.tuke.dt.videoconferenceapi.repository.RoleRepository;
import net.tuke.dt.videoconferenceapi.repository.UserRepository;
import net.tuke.dt.videoconferenceapi.security.JwtTokenProvider;
import net.tuke.dt.videoconferenceapi.service.AuthService;
import net.tuke.dt.videoconferenceapi.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {


    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ParticipantRepository participantRepository;
    private RefreshTokenService refreshTokenService;
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(JwtTokenProvider jwtTokenProvider,
                           AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           ParticipantRepository participantRepository,
                           PasswordEncoder passwordEncoder,
                           RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.participantRepository = participantRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }


    @Transactional
    @Override
    public LoginResponse login(LoginDTO loginDto) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication.getName());


        User user = userRepository.findByEmail(loginDto.getUsernameOrEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User", "usernameOrEmail", 123));
        Participant participant = user.getParticipant();

        String refreshToken = refreshTokenService.createToken(user);


        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token,"Bearer",refreshToken);

        return new LoginResponse( participant.getUsername(),user.getId() ,user.getName(),
                user.getPassword(), user.getSurname(),user.getEmail(),
                user.getRoles(),jwtAuthResponse);
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
        prt.setCreatedDate(new Date());
        user.setParticipant(prt);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();

        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "user registered successfully";
    }



}
