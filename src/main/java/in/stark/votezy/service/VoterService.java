package in.stark.votezy.service;

import in.stark.votezy.entity.Candidate;
import in.stark.votezy.entity.Vote;
import in.stark.votezy.entity.Voter;
import in.stark.votezy.exception.DuplicateResourceException;
import in.stark.votezy.exception.ResourceNotFoundException;
import in.stark.votezy.repository.CandidateRepository;
import in.stark.votezy.repository.VoterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public Voter updateVoter(Long id, Voter updatedVoter) {
        return voterRepository.findById(id)
                .map(existingVoter -> {
                    //add null check
                    existingVoter.setName(updatedVoter.getName());
                    existingVoter.setEmail(updatedVoter.getEmail());
                    return voterRepository.save(existingVoter);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Voter with id: %d not found".formatted(id)));
    }

    @Transactional
    public void deleteVoter(Long id) {
        Voter voter = voterRepository.findById(id).orElse(null);
        if(voter == null) {
            throw new ResourceNotFoundException("Voter with id:" + id + " not found");
        }
        Vote vote = voter.getCvote();
        if(vote!= null) {
            Candidate candidate = vote.getCandidate();
            candidate.setVoteCount(candidate.getVoteCount() - 1);
            candidateRepository.save(candidate);
        }

        voterRepository.delete(voter);
    }
}
