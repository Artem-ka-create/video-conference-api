package net.tuke.dt.videoconferenceapi.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User DTO model information")
public class UserDTO {

    private Long id;

    @Schema(description = "user name")
    private String name;

    @Schema(description = "user surname")
    private String surname;

    @Schema(description = "user password")
    private String password;

    @Schema(description = "user email")
    private String email;

    @Schema(description = "user link to participant утешен")
    private ParticipantDTO participant;

    @Schema(description = "list of rooms where user is an attendee")
    private List<SubUserRoomsDTO> rooms;

}
