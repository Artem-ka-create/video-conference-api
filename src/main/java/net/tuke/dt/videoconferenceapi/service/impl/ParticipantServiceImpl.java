package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.dto.ParticipantDTO;
import net.tuke.dt.videoconferenceapi.entity.Conference;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.exception.ResourceNotFoundException;
import net.tuke.dt.videoconferenceapi.playload.ParticipantResponse;
import net.tuke.dt.videoconferenceapi.repository.ConferenceRepository;
import net.tuke.dt.videoconferenceapi.repository.ParticipantRepository;
import net.tuke.dt.videoconferenceapi.service.ParticipantService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private ModelMapper modelMapper;
    private ParticipantRepository participantRepository;

    public ParticipantServiceImpl(ModelMapper modelMapper, ParticipantRepository participantRepository) {
        this.modelMapper = modelMapper;
        this.participantRepository = participantRepository;
    }

    @Override
    public ParticipantDTO createParticipant(ParticipantDTO participantDto) {

        Participant participant = mapToEntity(participantDto);
        participant.setCreatedDate(new Date());
        Participant newParticipant = participantRepository.save(participant);

        return mapToDto(newParticipant);
    }

    @Override
    public ParticipantDTO getParticipantById(Long id) {

        Participant participant = participantRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Participant", "id", id));

        return mapToDto(participant);
    }

    @Override
    public void deleteParticipant(Long id) {
        Participant post = participantRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Participant", "id", id));

        participantRepository.delete(post);
    }

    @Override
    public ParticipantResponse getAllParticipants(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Participant> participants = participantRepository.findAll(pageable);

        //get content from page
        List<Participant> listOfParticipants = participants.getContent();
        List<ParticipantDTO> content =  listOfParticipants.stream().map(participant -> mapToDto(participant)).collect(Collectors.toList());
        ParticipantResponse postResponse = new ParticipantResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(participants.getNumber());
        postResponse.setPageSize(participants.getSize());
        postResponse.setTotalPages(participants.getTotalPages());
        postResponse.setTotalElements(participants.getTotalElements());
        postResponse.setLast(participants.isLast());

        return postResponse;
    }


    private ParticipantDTO mapToDto(Participant post){

        ParticipantDTO postDto = modelMapper.map(post,ParticipantDTO.class);
        return postDto;
    }
    private Participant mapToEntity(ParticipantDTO postDto){

        Participant post = modelMapper.map(postDto,Participant.class);
        return post;
    }
}
