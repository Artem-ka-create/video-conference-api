package net.tuke.dt.videoconferenceapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "SubUser DTO model information")
public class SubUserDTO {
    private Long id;

    @Schema(description = "SubUserDTO entity name")
    private String name;

    @Schema(description = "SubUserDTO entity surname")
    private String surname;

    @Schema(description = "SubUserDTO entity password")
    private String password;

    @Schema(description = "SubUserDTO entity email")
    private String email;

    @Schema(description = "Entity participant")
    private ParticipantDTO participant;

}
