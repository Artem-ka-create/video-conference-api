package net.tuke.dt.videoconferenceapi.service;

import net.tuke.dt.videoconferenceapi.dto.ParticipantDTO;
import net.tuke.dt.videoconferenceapi.playload.ParticipantResponse;

public interface ParticipantService {

    ParticipantDTO createParticipant(ParticipantDTO participantDto);
    ParticipantDTO getParticipantById(Long id);

    void deleteParticipant(Long id);
    ParticipantResponse getAllParticipants(int pageNo, int pageSize, String sortBy, String sortDir);


}
