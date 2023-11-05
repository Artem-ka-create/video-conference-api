package net.tuke.dt.videoconferenceapi.playload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JWTAuthResponse {
    private String accessToken;
    private String tokenType= "Bearer";
}
