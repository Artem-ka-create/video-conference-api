package net.tuke.dt.videoconferenceapi.playload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String username;
    private String name;
    private String surname;
    private String password;
    private String email;

}
