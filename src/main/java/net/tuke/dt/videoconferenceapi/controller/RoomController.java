package net.tuke.dt.videoconferenceapi.controller;

import net.tuke.dt.videoconferenceapi.dto.RoomDTO;
import net.tuke.dt.videoconferenceapi.entity.Room;
import net.tuke.dt.videoconferenceapi.service.RoomService;
import net.tuke.dt.videoconferenceapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("owners/{owner-id}")
    ResponseEntity<RoomDTO> createRoomByOwner(@RequestBody RoomDTO roomDTO,@PathVariable(name = "owner-id") Long ownerId){
        RoomDTO createdRoom = roomService.createRoom(roomDTO, ownerId);
        return new ResponseEntity<>( createdRoom, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    ResponseEntity<RoomDTO> getRoomById(@PathVariable(name = "id") Long id){
        RoomDTO foundRoom = roomService.findRoomById(id);
        return new ResponseEntity<>( foundRoom, HttpStatus.OK);
    }
    @PutMapping("{room-id}/conferences/{conference-id}")
    ResponseEntity<RoomDTO> createRoomByOwner(@PathVariable(name = "room-id") Long roomId,@PathVariable(name = "conference-id") Long ownerId){
        RoomDTO createdRoom = roomService.addConferenceToRoom(roomId, ownerId);
        return new ResponseEntity<>( createdRoom, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteRoomById(@PathVariable(name = "id") Long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>( "Room Successfully deleted" , HttpStatus.OK);
    }
    @PutMapping("{id}")
    ResponseEntity<RoomDTO> updateRoomById(@RequestBody RoomDTO roomDTO ,@PathVariable(name = "id") Long id){
        RoomDTO updatedRoom = roomService.updateRoom(roomDTO,id);
        return new ResponseEntity<>(updatedRoom , HttpStatus.OK);
    }
    @GetMapping("users/{user-id}")
    ResponseEntity<List<RoomDTO>> getRoomsByUser(@PathVariable(name = "user-id") Long userId){

        List<RoomDTO> roomList = roomService.getRoomsByUserId(userId);
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }
// TODO: SubParticipantEntity
}