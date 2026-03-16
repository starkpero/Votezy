package in.stark.votezy.service;

import in.stark.votezy.entity.Candidate;
import in.stark.votezy.entity.Vote;
import in.stark.votezy.exception.ResourceNotFoundException;
import in.stark.votezy.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {
    private CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));
    }

    public Candidate updateCandidate(Long id, Candidate updatedCandidate){
        Candidate cnd = getCandidateById(id);
        if(updatedCandidate.getName()!=null) {
            cnd.setName(updatedCandidate.getName());
        }
        if(updatedCandidate.getParty()!=null) {
            cnd.setParty(updatedCandidate.getParty());
        }
        return candidateRepository.save(cnd);
    }

    public void deleteCandidate(Long id) {
        Candidate cnd = getCandidateById(id);
        List<Vote> votes = cnd.getVote();
        for(Vote v: votes) {
            v.setCandidate(null);
        }
        cnd.getVote().clear();
        candidateRepository.delete(cnd);
    }
}
