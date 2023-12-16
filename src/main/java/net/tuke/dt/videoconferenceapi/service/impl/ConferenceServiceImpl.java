package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.dto.ConferenceDTO;
import net.tuke.dt.videoconferenceapi.dto.JoinConferenceDTO;
import net.tuke.dt.videoconferenceapi.dto.NewConferenceEventDTO;
import net.tuke.dt.videoconferenceapi.entity.Conference;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.exception.ResourceNotFoundException;
import net.tuke.dt.videoconferenceapi.repository.ConferenceRepository;
import net.tuke.dt.videoconferenceapi.repository.ParticipantRepository;
import net.tuke.dt.videoconferenceapi.service.ConferenceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public ConferenceDTO addNewConferenceEvent(NewConferenceEventDTO newConferenceData) {
        if (conferenceRepository.findConferenceByConferenceNameAndCompletedDateNull(newConferenceData.getConferenceName()).isPresent()){
            throw new RuntimeException("THis conference is running, you can join");
        }

        Optional<Participant> participant = participantRepository
                .findParticipantByUsername(newConferenceData.getParticipantName());

        Conference createdConference =
                participant.isPresent() && participant.get().getUser()!=null?
                initializeConferenceWithExistingParticipant(participant.get(), newConferenceData):
                initializeNewConferenceNewParticipant(newConferenceData);

        return mapToDto(createdConference);
    }

    @Override
    @Transactional
    public ConferenceDTO joinParticipantToConference(JoinConferenceDTO joinConferenceData) {

        Conference conference = conferenceRepository
                .findConferenceByConferenceNameAndCompletedDateNull(joinConferenceData.getConferenceName()).orElseThrow(()->
                        new ResourceNotFoundException("Conference", "name", joinConferenceData.getConferenceName()));

        Participant participant = new Participant();
        if (joinConferenceData.getUserId()==null){

            participant.setCreatedDate(new Date());
            participant.setUsername(joinConferenceData.getUsername());

        }else{
            participant = participantRepository.findById(joinConferenceData.getUserId()).orElseThrow(()->
                    new ResourceNotFoundException("Participant", "id", joinConferenceData.getUserId()));
        }

        participant.addConference(conference);
        conference.addParticipant(participant);

        participantRepository.save(participant);

        return mapToDto(conferenceRepository.save(conference));
    }

    @Override
    public ConferenceDTO finishConference(String conferenceName) {
        System.out.println("Finish this meeting -> " + conferenceName);
        Conference conference = conferenceRepository
                .findConferenceByConferenceNameAndCompletedDateNull(conferenceName).orElseThrow(()->
                        new ResourceNotFoundException("Conference", "id", conferenceName));

        conference.setCompletedDate(new Date());

        System.out.println(conference.getCompletedDate());

        return mapToDto(conferenceRepository.save(conference));

    }

    private Conference initializeNewConferenceNewParticipant(NewConferenceEventDTO newConferenceData) {

        Participant part = new Participant();
        part.setUsername(newConferenceData.getParticipantName());
        part.setCreatedDate(new Date());

        Conference conference = new Conference();
        conference.setConferenceName(newConferenceData.getConferenceName());
        conference.setAttendeePassword(newConferenceData.getAttendeePassword());
        conference.setTechnology(newConferenceData.getTechnology());
        conference.setModeratorPassword(newConferenceData.getModeratorPassword());
        conference.setCreatedDate(new Date());

        part.addConference(conference);
        conference.addParticipant(part);

        participantRepository.save(part);

        return conferenceRepository.save(conference);
    }
    private Conference initializeConferenceWithExistingParticipant(Participant participant, NewConferenceEventDTO newConferenceData){

        Conference conference = new Conference();
        conference.setConferenceName(newConferenceData.getConferenceName());
        conference.setAttendeePassword(newConferenceData.getAttendeePassword());
        conference.setTechnology(newConferenceData.getTechnology());
        conference.setModeratorPassword(newConferenceData.getModeratorPassword());
        conference.addParticipant(participant);
        conference.setCreatedDate(new Date());

        participant.addConference(conference);

        participantRepository.save(participant);

        return conferenceRepository.save(conference);
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
