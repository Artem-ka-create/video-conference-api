package net.tuke.dt.videoconferenceapi.service;

import net.tuke.dt.videoconferenceapi.dto.ConferenceDTO;
import net.tuke.dt.videoconferenceapi.dto.ParticipantDTO;

public interface ConferenceService {

    ConferenceDTO createConference(ConferenceDTO conferenceDTO);

    ConferenceDTO addParticipantToConference (Long conferenceId, Long participantId);

    void deleteConference(Long id);

    ConferenceDTO findConferenceById(Long id);

}
