package net.tuke.dt.videoconferenceapi.controller;


import net.tuke.dt.videoconferenceapi.dto.UserDTO;
import net.tuke.dt.videoconferenceapi.entity.User;
import net.tuke.dt.videoconferenceapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    ResponseEntity<UserDTO> findUserById(@PathVariable Long id){
        UserDTO foundUser = userService.findUserById(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @PutMapping("{id}")
    ResponseEntity<UserDTO> updateUserById(@PathVariable(name = "id") Long id, @RequestBody UserDTO userDto){
        UserDTO updatedUser = userService.updateUserById(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("{user-id}/rooms/{room-id}/remove")
    ResponseEntity<UserDTO> removeUserFromRoom( @PathVariable(name = "user-id") Long userId,
                                                @PathVariable(name = "room-id") Long roomId){
        UserDTO updatedUser = userService.removeUserFromRoom(userId,roomId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @PutMapping("{user-id}/rooms/{room-id}/add")
    ResponseEntity<UserDTO> addUserFromRoom( @PathVariable(name = "user-id") Long userId,
                                             @PathVariable(name = "room-id") Long roomId){
        UserDTO updatedUser = userService.addUserToRoom(userId,roomId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
