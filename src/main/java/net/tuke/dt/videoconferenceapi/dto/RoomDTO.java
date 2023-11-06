package net.tuke.dt.videoconferenceapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.tuke.dt.videoconferenceapi.entity.Conference;
import net.tuke.dt.videoconferenceapi.entity.User;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Room DTO model information")
public class RoomDTO {

    private Long id;

    @Schema(description = "Room name")
    private String name;

    @Schema(description = "Room owner Id")
    private Long ownerId;

    @Schema(description = "Room color Tag")
    private String colorTag;

    @Schema(description = "Room date of creation")
    private Date createdDate;

    @Schema(description = "List of conferences")
    private List<ConferenceDTO> conferences;

    @Schema(description = "List of users in room")
    private List<SubUserDTO> users;

}
