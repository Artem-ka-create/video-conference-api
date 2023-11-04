package net.tuke.dt.videoconferenceapi.playload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {

    private String usernameOrEmail;
    private String password;

}
