package net.tuke.dt.videoconferenceapi.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.tuke.dt.videoconferenceapi.entity.Participant;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Conference DTO model information")
public class ConferenceDTO {

    private Long id;
    @Schema(description = "conference name")
    private String conferenceName;

    @Schema(description = "date of creation conference")
    private Date createdDate;

    @Schema(description = "date of completing conference")
    private Date completedDate;

    @Schema(description = "list if participants which where at conference")
    private List<ParticipantDTO> participants;

}
