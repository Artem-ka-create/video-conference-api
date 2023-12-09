package net.tuke.dt.videoconferenceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.tuke.dt.videoconferenceapi.entity.Participant;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NewConferenceEventDTO {

    private String conferenceName;
    private String participantName;
    private String technology;
    private String attendeePassword;
    private String moderatorPassword;


}
