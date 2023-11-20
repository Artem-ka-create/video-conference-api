package net.tuke.dt.videoconferenceapi.service;

import net.tuke.dt.videoconferenceapi.playload.LoginDTO;
import net.tuke.dt.videoconferenceapi.playload.LoginResponse;
import net.tuke.dt.videoconferenceapi.playload.RegisterDTO;

public interface AuthService {
    LoginResponse login(LoginDTO loginDto);

    String register(RegisterDTO registerDTO);
}
