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
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Participant DTO model information")
public class ParticipantDTO {


    private Long id;

    @Schema(description = "participant username")
    private String username;

    @Schema(description = "Date of creation of participant")
    private Date createdDate;



}
