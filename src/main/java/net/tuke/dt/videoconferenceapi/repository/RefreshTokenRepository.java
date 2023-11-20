package net.tuke.dt.videoconferenceapi.repository;


import net.tuke.dt.videoconferenceapi.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
