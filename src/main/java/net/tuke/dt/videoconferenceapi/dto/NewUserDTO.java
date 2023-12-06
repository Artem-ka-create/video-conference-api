package net.tuke.dt.videoconferenceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUserDTO {
    private String newUserEmail;
    private Long newUserId;
    private Long roomId;
    private Long initiatorUserId;
}
