package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.entity.RefreshToken;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.playload.JWTAuthResponse;
import net.tuke.dt.videoconferenceapi.playload.JwtRequestRefreshDto;
import net.tuke.dt.videoconferenceapi.repository.RefreshTokenRepository;
import net.tuke.dt.videoconferenceapi.security.JwtTokenProvider;
import net.tuke.dt.videoconferenceapi.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.ZoneId;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    @Value("${app.refresh-token-expiration-milliseconds}")
    private int exp;

    private JwtTokenProvider jwtTokenProvider;
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,JwtTokenProvider jwtTokenProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public String createToken(User user) {
        RefreshToken token = new RefreshToken();

        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiration(ZonedDateTime.now().plusMinutes(exp));

        var refreshToken = refreshTokenRepository.save(token);

        return refreshToken.getToken();
    }

    @Override
    public JWTAuthResponse refreshToken(JwtRequestRefreshDto jwtRequestRefreshDto) {

        var tokenOpt = refreshTokenRepository.findRefreshTokenByToken(jwtRequestRefreshDto.getRefreshToken());
        if (tokenOpt.isEmpty()){
            throw new RuntimeException("Refresh token %s not found".formatted(jwtRequestRefreshDto.getRefreshToken()));
        }

        var token = tokenOpt.get();
        if (isTokenExpired(token.getExpiration())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh Token %s was expired!".formatted(jwtRequestRefreshDto.getRefreshToken()));
        }
        String jwt = jwtTokenProvider.generateToken(token.getUser().getEmail());
        updateToken(token);

        return new JWTAuthResponse(jwt," Bearer" ,token.getToken());
    }


    private void updateToken(RefreshToken token) {
        token.setExpiration(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(exp));
        refreshTokenRepository.save(token);
    }

    private boolean isTokenExpired(ZonedDateTime expirationTime) {
        return expirationTime.isBefore(ZonedDateTime.now(ZoneId.systemDefault()));
    }

}














