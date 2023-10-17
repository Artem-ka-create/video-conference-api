package net.tuke.dt.videoconferenceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubUserDTO {
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private ParticipantDTO participant;

}
