package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.dto.ConferenceDTO;
import net.tuke.dt.videoconferenceapi.entity.Conference;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.exception.ResourceNotFoundException;
import net.tuke.dt.videoconferenceapi.repository.ConferenceRepository;
import net.tuke.dt.videoconferenceapi.repository.ParticipantRepository;
import net.tuke.dt.videoconferenceapi.service.ConferenceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private ConferenceRepository conferenceRepository;
    private ParticipantRepository participantRepository;
    private ModelMapper modelMapper;

    public ConferenceServiceImpl(ParticipantRepository participantRepository,ConferenceRepository conferenceRepository, ModelMapper modelMapper) {
        this.conferenceRepository = conferenceRepository;
        this.modelMapper = modelMapper;
        this.participantRepository = participantRepository;
    }

    @Override
    public ConferenceDTO createConference(ConferenceDTO conferenceDTO) {

        Conference conference = mapToEntity(conferenceDTO);
        conference.setCreatedDate(new Date());
        Conference newParticipant = conferenceRepository.save(conference);

        return mapToDto(newParticipant);
    }

    @Override
//    @Transactional
    public ConferenceDTO addParticipantToConference(Long conferenceId, Long participantId) {

        Participant participant = participantRepository.findById(participantId).orElseThrow(()->
                new ResourceNotFoundException("Participant", "id", participantId));
        Conference conference = conferenceRepository.findById(conferenceId).orElseThrow(()->
                new ResourceNotFoundException("Conference", "id", conferenceId));

        participant.addConference(conference);
        conference.addParticipant(participant);
        participantRepository.save(participant);
        Conference updatedConference = conferenceRepository.save(conference);


        return mapToDto(updatedConference);
    }

    @Override
    public void deleteConference(Long id) {
        Conference conference = conferenceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Conference", "id", id));

        List<Participant> participantList = conference.getParticipants();
        participantList.stream().forEach(p->{
            p.deleteConference(conference);
            participantRepository.save(p);
        });


        conferenceRepository.delete(conference);

    }

    @Override
    public ConferenceDTO findConferenceById(Long id) {
        Conference conference = conferenceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Conference", "id", id));
        return mapToDto(conference);

    }


    private ConferenceDTO mapToDto(Conference conference){

        ConferenceDTO conferenceDto = modelMapper.map(conference,ConferenceDTO.class);
        return conferenceDto;
    }
    private Conference mapToEntity(ConferenceDTO conferenceDto){

        Conference conference = modelMapper.map(conferenceDto,Conference.class);
        return conference;
    }
}
