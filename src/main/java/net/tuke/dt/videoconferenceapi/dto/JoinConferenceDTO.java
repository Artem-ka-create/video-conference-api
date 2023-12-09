package net.tuke.dt.videoconferenceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JoinConferenceDTO {

    private String conferenceName;
    private String username;
    private Long userId;
}
