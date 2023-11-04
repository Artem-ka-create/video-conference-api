package net.tuke.dt.videoconferenceapi.repository;

import net.tuke.dt.videoconferenceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByTag(String Tag);

    Boolean existsByTag(String Tag);
}
