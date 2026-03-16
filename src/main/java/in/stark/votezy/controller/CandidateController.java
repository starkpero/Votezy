package in.stark.votezy.controller;

import in.stark.votezy.entity.Candidate;
import in.stark.votezy.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
@CrossOrigin
public class CandidateController {
    private CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/add")
    public ResponseEntity<Candidate> addCandidate(@RequestBody @Valid Candidate candidate) {
        Candidate cnd = candidateService.addCandidate(candidate);
        return new ResponseEntity<Candidate>(cnd, HttpStatus.CREATED);
    }

    @GetMapping("/fetchAllCandidates")
    public ResponseEntity<List<Candidate>> fetchAllCandidates(){
        List<Candidate> cnds = candidateService.getAllCandidates();
        return new ResponseEntity<>(cnds, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable("id") Long id) {
        Candidate cnd = candidateService.getCandidateById(id);
        return new ResponseEntity<>(cnd, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody Candidate candidate) {
        Candidate cnd = candidateService.updateCandidate(id, candidate);
        return new ResponseEntity<>(cnd, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return new ResponseEntity<>("Candidate successfully deleted", HttpStatus.OK);
    }
}
