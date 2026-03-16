package in.stark.votezy.controller;

import in.stark.votezy.dto.VoteRequestDTO;
import in.stark.votezy.dto.VoteResponseDTO;
import in.stark.votezy.entity.Vote;
import in.stark.votezy.service.VotingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin
public class VotingController {
    private VotingService votingService;

    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping("/cast")
    public ResponseEntity<VoteResponseDTO> castVote(@RequestBody @Valid VoteRequestDTO voteRequest) {
        VoteResponseDTO vote = votingService.castVote(voteRequest.getVoterId(), voteRequest.getCandidateId());
        return new ResponseEntity<>(vote, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Vote>> getAllVotes(){
        List<Vote> votes = votingService.getAllVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }
}
