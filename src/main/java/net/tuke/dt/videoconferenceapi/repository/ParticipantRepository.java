package net.tuke.dt.videoconferenceapi.repository;

import net.tuke.dt.videoconferenceapi.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findParticipantByUsername (String username);
}
