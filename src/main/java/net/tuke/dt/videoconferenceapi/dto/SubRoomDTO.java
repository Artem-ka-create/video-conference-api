package net.tuke.dt.videoconferenceapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.tuke.dt.videoconferenceapi.entity.Conference;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "SubRoom DTO model information")
public class SubRoomDTO {

    private Long id;

    @Schema(description = "SubRoom entity name")
    private String name;

    @Schema(description = "SubRoom entity owner Id")
    private Long ownerId;

    @Schema(description = "SubRoom entity colorTag")
    private String colorTag;

    @Schema(description = "SubRoom entity creation Date")
    private Date createdDate;

    @Schema(description = "List of conferences")
    private List<Conference> conferences;

}
