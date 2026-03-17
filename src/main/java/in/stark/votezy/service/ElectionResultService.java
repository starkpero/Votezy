package in.stark.votezy.service;

import in.stark.votezy.dto.ElectionResultResponseDTO;
import in.stark.votezy.entity.Candidate;
import in.stark.votezy.entity.ElectionResult;
import in.stark.votezy.exception.ResourceNotFoundException;
import in.stark.votezy.repository.CandidateRepository;
import in.stark.votezy.repository.ElectionResultRepository;
import in.stark.votezy.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionResultService {
    private ElectionResultRepository electionResultRepository;
    private VoteRepository voteRepository;
    private CandidateRepository candidateRepository;

    public ElectionResultService(ElectionResultRepository electionResultRepository, VoteRepository voteRepository, CandidateRepository candidateRepository) {
        this.electionResultRepository = electionResultRepository;
        this.voteRepository = voteRepository;
        this.candidateRepository = candidateRepository;
    }

    public ElectionResultResponseDTO declareElectionResult(String electionName) {
        Optional<ElectionResult> existingResult = electionResultRepository.findByElectionName(electionName);
        if (existingResult.isPresent()) {
            ElectionResult electionResult = existingResult.get();
            return mapToDto(electionResult);
        }
        if(voteRepository.count() == 0) {
            throw new IllegalStateException("Cannot declare the result as no vote has been casted");
        }

        //calculate winner
        List<Candidate> candidates = candidateRepository.findAllByOrderByVoteCountDesc();
        Candidate winner = candidates.stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No candidates found"));

        int totalVotes = candidates.stream().mapToInt(Candidate::getVoteCount).sum();

        ElectionResult electionResult = new ElectionResult();
        electionResult.setElectionName(electionName);
        electionResult.setWinner(winner);
        electionResult.setTotalVotes(totalVotes);

        return mapToDto(electionResultRepository.save(electionResult));
    }

    public List<ElectionResult> getALlResults(){
        return electionResultRepository.findAll();
    }

    private ElectionResultResponseDTO mapToDto(ElectionResult entity) {
        return new ElectionResultResponseDTO(
                entity.getElectionName(),
                entity.getTotalVotes(),
                entity.getWinnerId(),
                entity.getWinner() != null ? entity.getWinner().getVoteCount() : 0,
                entity.getWinnerName()
        );
    }
}
