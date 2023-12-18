package net.tuke.dt.videoconferenceapi.repository;

import net.tuke.dt.videoconferenceapi.entity.Conference;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    Optional<Conference> findConferenceByConferenceNameAndCompletedDateNull(String conferenceName);
    Optional<Conference> findConferenceByConferenceName(String conferenceName);

    Optional<Conference> findConferenceByRoom(Room room);

}
