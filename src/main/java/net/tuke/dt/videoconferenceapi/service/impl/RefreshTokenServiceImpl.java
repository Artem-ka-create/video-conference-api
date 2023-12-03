package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.entity.RefreshToken;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.exception.ResourceNotFoundException;
import net.tuke.dt.videoconferenceapi.playload.JWTAuthResponse;
import net.tuke.dt.videoconferenceapi.playload.JwtRequestRefreshDto;
import net.tuke.dt.videoconferenceapi.repository.RefreshTokenRepository;
import net.tuke.dt.videoconferenceapi.security.JwtTokenProvider;
import net.tuke.dt.videoconferenceapi.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.time.ZoneId;

import java.time.ZonedDateTime;
import java.util.Optional;
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


    @Transactional
    @Override
    public String createToken(User user) {

        Optional<RefreshToken> foundToken = refreshTokenRepository.findRefreshTokenByUserEmail(user.getEmail());

        return foundToken.isEmpty() ? mapRefreshToken(new RefreshToken(),user) :  foundToken.get().getToken();
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

    public String getRefreshTokenByUser(User user){

        RefreshToken refreshToken= refreshTokenRepository.getRefreshTokenByUser(user).orElseThrow(() ->
                new ResourceNotFoundException("RefreshToken", "userId", user.getId()));

        return refreshToken.getToken();
    }


    private void updateToken(RefreshToken token) {
        token.setExpiration(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(exp));
        refreshTokenRepository.save(token);
    }

    private boolean isTokenExpired(ZonedDateTime expirationTime) {
        return expirationTime.isBefore(ZonedDateTime.now(ZoneId.systemDefault()));
    }

    private String mapRefreshToken(RefreshToken refreshToken,User user){

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiration(ZonedDateTime.now().plusMinutes(exp));

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        return savedToken.getToken();
    }


}














