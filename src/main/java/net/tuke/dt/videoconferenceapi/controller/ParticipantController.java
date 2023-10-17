package net.tuke.dt.videoconferenceapi.controller;

import net.tuke.dt.videoconferenceapi.dto.ParticipantDTO;
import net.tuke.dt.videoconferenceapi.entity.Participant;
import net.tuke.dt.videoconferenceapi.playload.ParticipantResponse;
import net.tuke.dt.videoconferenceapi.service.ParticipantService;
import net.tuke.dt.videoconferenceapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/participants")
public class ParticipantController {

    private ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO participantDto){
        ParticipantDTO participant = participantService.createParticipant(participantDto);
        return new ResponseEntity<>(participant , HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    ResponseEntity<ParticipantDTO> getParticipandById(@PathVariable(name = "id") Long id){
        ParticipantDTO foundParticipant = participantService.getParticipantById(id);
        return ResponseEntity.ok(foundParticipant);
    }

    @GetMapping
    public ParticipantResponse getAllPosts (
            @RequestParam (value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam (value="pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam (value="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam (value="sortDir", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortDir
    ) {
        return participantService.getAllParticipants(pageNo, pageSize, sortBy, sortDir);
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteParticipantById(@PathVariable(name = "id") Long id){
        participantService.deleteParticipant(id);
        return ResponseEntity.ok("Successfully deleted");
    }

}
