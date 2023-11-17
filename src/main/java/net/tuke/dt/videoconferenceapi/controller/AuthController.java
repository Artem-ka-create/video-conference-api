package net.tuke.dt.videoconferenceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.tuke.dt.videoconferenceapi.playload.JWTAuthResponse;
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


    @Operation(
            summary = "Login to application",
            description = "This endpoint is used to login to application using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }


    @Operation(
            summary = "Register to application",
            description = "This endpoint is used to register to application using REST API."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 CREATED"
    )
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
