package net.tuke.dt.videoconferenceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.tuke.dt.videoconferenceapi.dto.ParticipantDTO;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.playload.ParticipantResponse;
import net.tuke.dt.videoconferenceapi.service.ParticipantService;
import net.tuke.dt.videoconferenceapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "REST APIs for Participant")
@RequestMapping("api/v1/participants")
public class ParticipantController {

    private ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Operation(
            summary = "Create participant",
            description = "This endpoint is used to create participant using REST API"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 CREATED"
    )
    @PostMapping
    ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO participantDto){
        ParticipantDTO participant = participantService.createParticipant(participantDto);
        return new ResponseEntity<>(participant , HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get participant by Id",
            description = "This endpoint is used to get participant by Id from database using REST API"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @GetMapping("{id}")
    ResponseEntity<ParticipantDTO> getParticipandById(@PathVariable(name = "id") Long id){
        ParticipantDTO foundParticipant = participantService.getParticipantById(id);
        return ResponseEntity.ok(foundParticipant);
    }


    @Operation(
            summary = "Get participants",
            description = "This endpoint is used to get all participants from database using REST API"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @GetMapping
    public ParticipantResponse getAllParticipants (
            @RequestParam (value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam (value="pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam (value="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam (value="sortDir", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortDir
    ) {
        return participantService.getAllParticipants(pageNo, pageSize, sortBy, sortDir);
    }


    @Operation(
            summary = "Delete participant by Id",
            description = "This endpoint is used to delete participant by Id from database using REST API"
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
    ResponseEntity<String> deleteParticipantById(@PathVariable(name = "id") Long id){
        participantService.deleteParticipant(id);
        return ResponseEntity.ok("Successfully deleted");
    }

}
