package net.tuke.dt.videoconferenceapi.controller;

import net.tuke.dt.videoconferenceapi.playload.LoginDTO;
import net.tuke.dt.videoconferenceapi.playload.RegisterDTO;
import net.tuke.dt.videoconferenceapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDto){
        String response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
