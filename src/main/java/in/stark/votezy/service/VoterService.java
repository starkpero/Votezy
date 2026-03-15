package in.stark.votezy.service;

import in.stark.votezy.entity.Voter;
import in.stark.votezy.exception.DuplicateResourceException;
import in.stark.votezy.exception.ResourceNotFoundException;
import in.stark.votezy.repository.CandidateRepository;
import in.stark.votezy.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VoterService {
    private VoterRepository voterRepository;
    private CandidateRepository candidateRepository;

    @Autowired
    public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    public Voter registerVoter(Voter voter) {
        if (voterRepository.existsByEmail(voter.getEmail())) {
            throw new DuplicateResourceException("Voter with email id:" + voter.getEmail() + " already exists");
        }
        return voterRepository.save(voter);
    }

    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public Voter getVoterById(Long id) {
        return voterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voter with id:" + id + " not found"));
    }
}
