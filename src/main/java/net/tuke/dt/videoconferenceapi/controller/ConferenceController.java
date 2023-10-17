package net.tuke.dt.videoconferenceapi.controller;


import net.tuke.dt.videoconferenceapi.dto.ConferenceDTO;
import net.tuke.dt.videoconferenceapi.entity.Conference;
import net.tuke.dt.videoconferenceapi.service.ConferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/conferences")
public class ConferenceController {

    private ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @PostMapping
    ResponseEntity<ConferenceDTO> createConference(@RequestBody ConferenceDTO conferenceDTO){
        ConferenceDTO createdConference = conferenceService.createConference(conferenceDTO);
        return new ResponseEntity<>(createdConference, HttpStatus.CREATED);
    }
    @PutMapping("{conference-id}/participants/{participant-id}")
    ResponseEntity<ConferenceDTO> addParticipantToConference(
            @PathVariable(name = "conference-id") Long conferenceId,
            @PathVariable(name = "participant-id") Long participantId

            ){
        ConferenceDTO updatedConference = conferenceService.addParticipantToConference(conferenceId,participantId);
        return new ResponseEntity<>(updatedConference ,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> addParticipantToConference( @PathVariable(name = "id") Long id){
        conferenceService.deleteConference(id);
        return new ResponseEntity<>("Conference successfully deleted" ,HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<ConferenceDTO> getConferenceById( @PathVariable(name = "id") Long id){
        ConferenceDTO foundConference = conferenceService.findConferenceById(id);
        return new ResponseEntity<>(foundConference ,HttpStatus.OK);
    }
}
