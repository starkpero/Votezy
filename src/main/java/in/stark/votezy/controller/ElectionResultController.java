package in.stark.votezy.controller;

import in.stark.votezy.dto.ElectionResultRequestDTO;
import in.stark.votezy.dto.ElectionResultResponseDTO;
import in.stark.votezy.entity.ElectionResult;
import in.stark.votezy.service.ElectionResultService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/result")
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
public class ElectionResultController {

    private ElectionResultService electionResultService;

    public ElectionResultController(ElectionResultService electionResultService) {
        this.electionResultService = electionResultService;
    }

    @PostMapping("/declare")
    public ResponseEntity<ElectionResultResponseDTO> declareElectionResult(@RequestBody @Valid ElectionResultRequestDTO
                                                                                   electionResultRequestDTO) {
        ElectionResultResponseDTO electionResult = electionResultService.declareElectionResult(electionResultRequestDTO.getElectionName());
        return new ResponseEntity<>(electionResult, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ElectionResult>> getAllResult(){
        List<ElectionResult> result = electionResultService.getALlResults();
        return ResponseEntity.ok(result);
    }
}
