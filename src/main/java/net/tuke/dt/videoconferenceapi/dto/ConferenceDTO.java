package net.tuke.dt.videoconferenceapi.dto;


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

public class ConferenceDTO {


    private Long id;
    private String conferenceName;
    private Date createdDate;
    private Date completedDate;
    private List<ParticipantDTO> participants;

}
