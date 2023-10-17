package net.tuke.dt.videoconferenceapi.service;

import net.tuke.dt.videoconferenceapi.dto.UserDTO;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO findUserById(Long id);

    UserDTO removeUserFromRoom(Long userId, Long roomId);

    UserDTO updateUserById(Long id, UserDTO userDTO);

    UserDTO addUserToRoom(Long userId, Long roomId);

}
