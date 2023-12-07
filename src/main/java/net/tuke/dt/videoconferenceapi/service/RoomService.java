package net.tuke.dt.videoconferenceapi.service;

import net.tuke.dt.videoconferenceapi.dto.NewUserDTO;
import net.tuke.dt.videoconferenceapi.dto.RoomDTO;

import java.util.List;

public interface RoomService {

//    create Room
    RoomDTO createRoom (RoomDTO roomDTO, Long ownerId);

//    add conference to room
    RoomDTO addConferenceToRoom(Long roomId, Long conferenceId);

//    find by id
    RoomDTO findRoomById(Long id);

//    update room
    RoomDTO updateRoom(RoomDTO roomDTO, Long roomId);

//    delete room
    void deleteRoom(Long roomId);

//    find by userId
    List<RoomDTO> getRoomsByUserId (Long userId);

    String deleteUserFromRoom(Long roomId, Long userId);

    RoomDTO addNewUserByEmail(NewUserDTO newUserDTO);
}
