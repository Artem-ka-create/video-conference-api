package net.tuke.dt.videoconferenceapi.repository;

import net.tuke.dt.videoconferenceapi.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
