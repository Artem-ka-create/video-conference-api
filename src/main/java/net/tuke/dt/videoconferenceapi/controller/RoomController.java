package net.tuke.dt.videoconferenceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.tuke.dt.videoconferenceapi.dto.RoomDTO;
import net.tuke.dt.videoconferenceapi.entity.Room;
import net.tuke.dt.videoconferenceapi.service.RoomService;
import net.tuke.dt.videoconferenceapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "REST APIs for Room")
@RequestMapping("api/v1/rooms")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @Operation(
            summary = "Create room",
            description = "This endpoint is used to create room using REST API."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping("owners/{owner-id}")
    ResponseEntity<RoomDTO> createRoomByOwner(@RequestBody RoomDTO roomDTO,@PathVariable(name = "owner-id") Long ownerId){
        RoomDTO createdRoom = roomService.createRoom(roomDTO, ownerId);
        return new ResponseEntity<>( createdRoom, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get room by Id",
            description = "This endpoint is used to get room using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @GetMapping("{id}")
    ResponseEntity<RoomDTO> getRoomById(@PathVariable(name = "id") Long id){
        RoomDTO foundRoom = roomService.findRoomById(id);
        return new ResponseEntity<>( foundRoom, HttpStatus.OK);
    }


    @Operation(
            summary = "Create room by user",
            description = "This endpoint is used to Create room by user Id using REST API. Sets this user as owner of room."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("{room-id}/conferences/{conference-id}")
    ResponseEntity<RoomDTO> createRoomByOwner(@PathVariable(name = "room-id") Long roomId,@PathVariable(name = "conference-id") Long ownerId){
        RoomDTO createdRoom = roomService.addConferenceToRoom(roomId, ownerId);
        return new ResponseEntity<>( createdRoom, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete room by Id",
            description = "This endpoint is used to delete room by Id from database using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteRoomById(@PathVariable(name = "id") Long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>( "Room Successfully deleted" , HttpStatus.OK);
    }


    @Operation(
            summary = "Update room",
            description = "This endpoint is used to update room by Id from database using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("{id}")
    ResponseEntity<RoomDTO> updateRoomById(@RequestBody RoomDTO roomDTO ,@PathVariable(name = "id") Long id){
        RoomDTO updatedRoom = roomService.updateRoom(roomDTO,id);
        return new ResponseEntity<>(updatedRoom , HttpStatus.OK);
    }

    @Operation(
            summary = "Get all rooms",
            description = "This endpoint is used to get all rooms from database using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @GetMapping("users/{user-id}")
    ResponseEntity<List<RoomDTO>> getRoomsByUser(@PathVariable(name = "user-id") Long userId){

        List<RoomDTO> roomList = roomService.getRoomsByUserId(userId);
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }
// TODO: SubParticipantEntity
}