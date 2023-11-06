package net.tuke.dt.videoconferenceapi.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.tuke.dt.videoconferenceapi.dto.ConferenceDTO;
import net.tuke.dt.videoconferenceapi.service.ConferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "REST APIs for Conference")
@RequestMapping("api/v1/conferences")
public class ConferenceController {

    private ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Operation(
            summary = "Create conference",
            description = "This endpoint is used to create conference using REST API, non-authorized users have access"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 CREATED"
    )
    @PostMapping
    ResponseEntity<ConferenceDTO> createConference(@RequestBody ConferenceDTO conferenceDTO){
        ConferenceDTO createdConference = conferenceService.createConference(conferenceDTO);
        return new ResponseEntity<>(createdConference, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Add participant to the conference",
            description = "This endpoint is used to add participant to the conference using REST API, non-authorized users have access"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @PutMapping("{conference-id}/participants/{participant-id}")
    ResponseEntity<ConferenceDTO> addParticipantToConference(
            @PathVariable(name = "conference-id") Long conferenceId,
            @PathVariable(name = "participant-id") Long participantId

            ){
        ConferenceDTO updatedConference = conferenceService.addParticipantToConference(conferenceId,participantId);
        return new ResponseEntity<>(updatedConference ,HttpStatus.OK);
    }

    @Operation(
            summary = "Delete conference",
            description = "This endpoint is used to delete conference from database using REST API"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteConference( @PathVariable(name = "id") Long id){
        conferenceService.deleteConference(id);
        return new ResponseEntity<>("Conference successfully deleted" ,HttpStatus.OK);
    }

    @Operation(
            summary = "Get conference by Id",
            description = "This endpoint is used to get conference by Id from database using REST API"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @GetMapping("{id}")
    ResponseEntity<ConferenceDTO> getConferenceById( @PathVariable(name = "id") Long id){
        ConferenceDTO foundConference = conferenceService.findConferenceById(id);
        return new ResponseEntity<>(foundConference ,HttpStatus.OK);
    }
}
