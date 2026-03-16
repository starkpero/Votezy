package in.stark.votezy.controller;

import in.stark.votezy.entity.Voter;
import in.stark.votezy.service.VoterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
@CrossOrigin
public class VoterController {
    private VoterService voterService;

    @Autowired
    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping("/register")
    public ResponseEntity<Voter> registerVoter(@RequestBody @Valid Voter voter) {
        Voter savedVoter = voterService.registerVoter(voter);
        return new ResponseEntity<>(savedVoter, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voter> getVoterById(@PathVariable("id") Long id) {
        Voter voter = voterService.getVoterById(id);
        return new ResponseEntity<>(voter, HttpStatus.OK);
    }

    @GetMapping("/getAllVoters")
    public ResponseEntity<List<Voter>> getAllVoters() {
        List<Voter> voterList = voterService.getAllVoters();
        return new ResponseEntity<>(voterList, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Voter> updatedVoter(@PathVariable Long id, @RequestBody Voter voter) {
        Voter updatedVoter = voterService.updateVoter(id, voter);
        return new ResponseEntity<>(updatedVoter, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVoter(@PathVariable Long id) {
        voterService.deleteVoter(id);
        return new ResponseEntity<>("Voter deleted", HttpStatus.OK);
    }

}
