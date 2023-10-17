package net.tuke.dt.videoconferenceapi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.entity.Room;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private ParticipantDTO participant;
    private List<subUserRoomsDTO> rooms;

}
