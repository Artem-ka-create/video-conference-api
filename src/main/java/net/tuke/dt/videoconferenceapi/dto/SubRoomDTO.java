package net.tuke.dt.videoconferenceapi.dto;

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
public class SubRoomDTO {

    private Long id;
    private String name;
    private Long ownerId;
    private String colorTag;
    private Date createdDate;
    private List<Conference> conferences;

}
