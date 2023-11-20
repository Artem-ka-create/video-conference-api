package net.tuke.dt.videoconferenceapi.playload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.tuke.dt.videoconferenceapi.entity.Role;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String username;
    private Long id;
    private String name;
    private String password;
    private String surname;
    private String email;
    private Set<Role> roles;
    private JWTAuthResponse jwtAuthResponse;
}
