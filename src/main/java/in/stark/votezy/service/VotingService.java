package in.stark.votezy.service;

import in.stark.votezy.dto.VoteResponseDTO;
import in.stark.votezy.entity.Candidate;
import in.stark.votezy.entity.Vote;
import in.stark.votezy.entity.Voter;
import in.stark.votezy.exception.ResourceNotFoundException;
import in.stark.votezy.exception.VoteNotAllowedException;
import in.stark.votezy.repository.CandidateRepository;
import in.stark.votezy.repository.VoteRepository;
import in.stark.votezy.repository.VoterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingService {
    private VoteRepository voteRepository;
    private CandidateRepository candidateRepository;
    private VoterRepository voterRepository;

    @Autowired
    public VotingService(VoteRepository voteRepository, CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.voteRepository = voteRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    @Transactional
    public VoteResponseDTO castVote(Long voterId, Long candidateId) {
        if(!voterRepository.existsById(voterId)){
            throw new ResourceNotFoundException("Voter not found with id: "+ voterId);
        }

        if(!candidateRepository.existsById(candidateId)) {
            throw new ResourceNotFoundException("Candidate not found with id: "+ candidateId);
        }

        //DB calls can be reduced if we do findById() instead of existsById()
        Voter voter = voterRepository.findById(voterId).get();
        if(voter.isHasVoted()) {
            throw new VoteNotAllowedException("Voter ID: "+voterId+" has already casted vote");
        }
        Candidate cnd = candidateRepository.findById(candidateId).get();
        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setCandidate(cnd);
        voter.setCvote(vote);
        //voteRepository.save(vote);

        cnd.setVoteCount(cnd.getVoteCount() + 1);
        candidateRepository.save(cnd);

        voter.setHasVoted(true);
        voterRepository.save(voter);
        return new VoteResponseDTO(
                "Vote cast successfully",
                true,
                voter.getId(),
                cnd.getId(),
                cnd.getParty()
        );
    }

    public List<Vote> getAllVotes(){
        return voteRepository.findAll();
    }
}
