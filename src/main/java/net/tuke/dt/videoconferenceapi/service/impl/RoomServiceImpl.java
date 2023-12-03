package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.dto.*;
import net.tuke.dt.videoconferenceapi.entity.Conference;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.entity.Room;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.exception.ResourceNotFoundException;
import net.tuke.dt.videoconferenceapi.repository.ConferenceRepository;
import net.tuke.dt.videoconferenceapi.repository.RoomRepository;
import net.tuke.dt.videoconferenceapi.repository.UserRepository;
import net.tuke.dt.videoconferenceapi.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl  implements RoomService {

    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private ModelMapper modelMapper;

    public RoomServiceImpl(RoomRepository roomRepository,ConferenceRepository conferenceRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.userRepository=userRepository;
        this.conferenceRepository=conferenceRepository;
    }


    @Override
    @Transactional
    public RoomDTO createRoom(RoomDTO roomDTO, Long ownerId) {
        User user = userRepository.findById(ownerId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", ownerId));

        Room room =mapToEntity(roomDTO);
        room.setOwnerId(user.getId());
        room.setCreatedDate(new Date());
        room.setConferences(new ArrayList<>());
        room.addUser(user);

        System.out.println(room);
        Room savedRoom = roomRepository.save(room);
        user.addRoom(savedRoom);
        User updateduser = userRepository.save(user);


        return mapToDto(savedRoom);
    }

    @Override
    @Transactional
    public RoomDTO addConferenceToRoom(Long roomId, Long conferenceId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
                new ResourceNotFoundException("Room", "id", roomId));
        Conference conference = conferenceRepository.findById(conferenceId).orElseThrow(() ->
                new ResourceNotFoundException("Conference", "id", conferenceId));
        conference.setRoom(room);
        conferenceRepository.save(conference);

        return mapToDto(room);
    }

    @Override
    public RoomDTO findRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Room", "id", id));

        return mapToDto(room);
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO, Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()->
                new ResourceNotFoundException("Room", "id", roomId));

        if (roomDTO.getName()!=null){
            room.setName(roomDTO.getName());
        }
        if (roomDTO.getColorTag()!=null){
            room.setColorTag(roomDTO.getColorTag());
        }
        if (roomDTO.getOwnerId()!=null){
            room.setOwnerId(roomDTO.getOwnerId());
        }

        Room updatedRoom = roomRepository.save(room);
        return mapToDto(updatedRoom);
    }

    @Override
    @Transactional
    public void deleteRoom(Long roomId) {

        Room room = roomRepository.findById(roomId).orElseThrow(()->
                new ResourceNotFoundException("Room", "id", roomId));

//        room.setUsers(new ArrayList<>());
//        Room updatedRoom = roomRepository.save(room);
        List<User> usrs= room.getUsers();

        usrs.stream().forEach(e -> {
            e.deleteRoom(room);
            userRepository.save(e);
        });
        room.setUsers(new ArrayList<>());

        roomRepository.delete(room);


    }

    @Override
    @Transactional
    public List<RoomDTO> getRoomsByUserId(Long userId) {

        User usr = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User", "id", userId));

        List<Room> rooms = roomRepository.findByUsers(usr);
//

        return rooms.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    private RoomDTO mapToDto(Room room){

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setOwnerId(room.getOwnerId());
        roomDTO.setCreatedDate(room.getCreatedDate());
        roomDTO.setColorTag(room.getColorTag());

        List<SubUserDTO> subUsers = new ArrayList<>();
        List<ConferenceDTO> conferences = new ArrayList<>();



        for (User user: room.getUsers()) {
            ParticipantDTO participant = user.getParticipant()!=null ?
                 modelMapper.map(user.getParticipant(),ParticipantDTO.class) : null;

            SubUserDTO subuser =
                    new SubUserDTO(user.getId(), user.getName(),
                            user.getSurname(), user.getPassword(), user.getEmail(),participant);
            subUsers.add(subuser);
        }
        for (Conference conf: room.getConferences()) {
            List<ParticipantDTO> participants = new ArrayList<>();

            for (Participant part:  conf.getParticipants()) {
                participants.add(new ParticipantDTO(part.getId(), part.getUsername(), part.getCreatedDate()));
            }
            ConferenceDTO subconf =
                    new ConferenceDTO(conf.getId(), conf.getConferenceName(),
                            conf.getCreatedDate(), conf.getCompletedDate(), participants);
            conferences.add(subconf);
        }
        roomDTO.setConferences(conferences);
        roomDTO.setUsers(subUsers);
        
        
        return roomDTO;
    }
    private Room mapToEntity(RoomDTO conferenceDto){

        Room conference = modelMapper.map(conferenceDto,Room.class);
        return conference;
    }

}
