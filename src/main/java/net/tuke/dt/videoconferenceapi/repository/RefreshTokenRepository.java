package net.tuke.dt.videoconferenceapi.repository;


import net.tuke.dt.videoconferenceapi.entity.RefreshToken;
import net.tuke.dt.videoconferenceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);

    Optional<RefreshToken> findRefreshTokenByUserEmail(String userId);

    Optional<RefreshToken> getRefreshTokenByUser(User user);
}
