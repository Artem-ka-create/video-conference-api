package net.tuke.dt.videoconferenceapi.service.impl;

import net.tuke.dt.videoconferenceapi.dto.*;
import net.tuke.dt.videoconferenceapi.entity.Conference;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.entity.Room;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.exception.ResourceNotFoundException;
import net.tuke.dt.videoconferenceapi.repository.ParticipantRepository;
import net.tuke.dt.videoconferenceapi.repository.RoomRepository;
import net.tuke.dt.videoconferenceapi.repository.UserRepository;
import net.tuke.dt.videoconferenceapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private ParticipantRepository participantRepository;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository,
                           RoomRepository roomRepository,ParticipantRepository participantRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.participantRepository=participantRepository;
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {

        User user = mapToEntity(userDTO);
        Participant participant= new Participant();


        if (userDTO.getParticipant()!=null && userDTO.getParticipant().getUsername()!=null){
            participant.setUsername(userDTO.getParticipant().getUsername());
            participant.setCreatedDate(new Date());
            Participant savedParticipant = participantRepository.save(participant);
            user.setParticipant(savedParticipant);
        }
        else{
            user.setParticipant(null);
        }

        user.setRooms(new ArrayList<>());
        User createdUser = userRepository.save(user);

        return mapToDto(createdUser);
    }

    @Override
    @Transactional
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", id));
        user.setRooms(roomRepository.findByUsers(user));

        return mapToDto(user);
    }

    @Override
    @Transactional
    public UserDTO removeUserFromRoom(Long userId, Long roomId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        Room room = roomRepository.findById(roomId).orElseThrow(() ->
                new ResourceNotFoundException("Room", "id", roomId));

        if (room.getUsers().contains(user)){
            room.deleteUser(user);
        }
        if(user.getRooms().contains(room)){
            user.deleteRoom(room);
        }


        User updatedUser = userRepository.save(user);
        Room updatedRoom = roomRepository.save(room);

        return mapToDto(updatedUser);
    }

    @Override
    @Transactional
    public UserDTO updateUserById(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", id));

        if (userDTO.getName()!=null){
            user.setName(userDTO.getName());
        }
        if (userDTO.getSurname()!=null){
            user.setSurname(userDTO.getSurname());
        }
        if (userDTO.getEmail()!=null){
            user.setEmail(userDTO.getEmail());
        }
        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    @Transactional
    public UserDTO addUserToRoom(Long userId, Long roomId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", userId));

        Room room = roomRepository.findById(roomId).orElseThrow(() ->
                new ResourceNotFoundException("Room", "id", roomId));


        if (!room.getUsers().contains(user)){
            room.addUser(user);
        }
        if (!user.getRooms().contains(room)){
            user.addRoom(room);
        }

        User updatedUser = userRepository.save(user);
        Room roomUpdate = roomRepository.save(room);

        return mapToDto(updatedUser);
    }


    private UserDTO mapToDto(User user){

        UserDTO userDTO = new UserDTO();
        if (user.getParticipant()!=null){
            ParticipantDTO participant =  modelMapper.map(user.getParticipant(),ParticipantDTO.class);
            userDTO.setParticipant(participant);
        }
        else{
            userDTO.setParticipant(null);
        }

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        List<subUserRoomsDTO> subRooms = new ArrayList<>();
        for (Room r: user.getRooms()) {
            subUserRoomsDTO subroom =
                    new subUserRoomsDTO(r.getId(), r.getName(),
                            r.getOwnerId(), r.getColorTag(), r.getCreatedDate());
            subRooms.add(subroom);
        }
        userDTO.setRooms(subRooms);
        return userDTO;
    }
    private User mapToEntity(UserDTO conferenceDto){

        User conference = modelMapper.map(conferenceDto,User.class);
        return conference;
    }
}
