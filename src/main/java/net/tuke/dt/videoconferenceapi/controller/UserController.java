package net.tuke.dt.videoconferenceapi.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.tuke.dt.videoconferenceapi.dto.UserDTO;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "REST APIs for User")
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(
            summary = "Create user",
            description = "This endpoint is used to create user using REST API."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 CREATED"
    )
    @PostMapping
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get user",
            description = "This endpoint is used to Get user by Id from database using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @GetMapping("{id}")
    ResponseEntity<UserDTO> findUserById(@PathVariable Long id){
        UserDTO foundUser = userService.findUserById(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @Operation(
            summary = "Update user",
            description = "This endpoint is used to update user by Id from database using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("{id}")
    ResponseEntity<UserDTO> updateUserById(@PathVariable(name = "id") Long id, @RequestBody UserDTO userDto){
        UserDTO updatedUser = userService.updateUserById(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @Operation(
            summary = "Remove user from room ",
            description = "This endpoint is used to remove user from room by user-Id and room-Id from database using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("{user-id}/rooms/{room-id}/remove")
    ResponseEntity<UserDTO> removeUserFromRoom( @PathVariable(name = "user-id") Long userId,
                                                @PathVariable(name = "room-id") Long roomId){
        UserDTO updatedUser = userService.removeUserFromRoom(userId,roomId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @Operation(
            summary = "Add user to room ",
            description = "This endpoint is used to add user to room by user-Id and room-Id from database using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("{user-id}/rooms/{room-id}/add")
    ResponseEntity<UserDTO> addUserToRoom( @PathVariable(name = "user-id") Long userId,
                                             @PathVariable(name = "room-id") Long roomId){
        UserDTO updatedUser = userService.addUserToRoom(userId,roomId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
