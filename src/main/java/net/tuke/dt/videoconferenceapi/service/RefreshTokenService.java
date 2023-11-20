package net.tuke.dt.videoconferenceapi.service;

import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.playload.JWTAuthResponse;
import net.tuke.dt.videoconferenceapi.playload.JwtRequestRefreshDto;

public interface RefreshTokenService {
    String createToken(User user);

    JWTAuthResponse refreshToken(JwtRequestRefreshDto jwtRequestRefreshDto);
}
