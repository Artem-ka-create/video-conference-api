package net.tuke.dt.videoconferenceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import net.tuke.dt.videoconferenceapi.playload.*;
import net.tuke.dt.videoconferenceapi.service.AuthService;
import net.tuke.dt.videoconferenceapi.service.RefreshTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    private RefreshTokenService refreshTokenService;


    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDto){

        LoginResponse responseObj = authService.login(loginDto);
        return ResponseEntity.ok(responseObj);
    }

    @Operation(
            summary = "Register to application",
            description = "This endpoint is used to register to application using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Refresh JWT of user in application",
            description = "This endpoint is used to refresh jwt of user to application using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthResponse> refreshJwt(@RequestBody JwtRequestRefreshDto refreshDto){
        return new ResponseEntity<>(refreshTokenService.refreshToken(refreshDto),HttpStatus.OK);
    }

    @Operation(
            summary = "get credentials of user by JWT in application",
            description = "This endpoint is used to get credentials of user by JWT in application using REST API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @PostMapping("/login-credential")
    public ResponseEntity<LoginResponse> getUserCredentials( @RequestHeader("Authorization") String jwt ){

        return new ResponseEntity<>(authService.getUserCredentials(jwt),HttpStatus.OK);
    }





}
