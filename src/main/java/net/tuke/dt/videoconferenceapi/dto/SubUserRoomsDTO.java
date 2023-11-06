package net.tuke.dt.videoconferenceapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "SubUserRooms DTO model information")
public class SubUserRoomsDTO {

    private Long id;

    @Schema(description = "SubUserRoom entity name")
    private String name;

    @Schema(description = "SubUserRoom entity owner Id")
    private Long ownerId;

    @Schema(description = "SubUserRoom entity color tag")
    private String colorTag;

    @Schema(description = "SubUserRoom entity creation Date")
    private Date createdDate;
}
