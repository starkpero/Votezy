package in.stark.votezy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "voter_id", unique = true) //owner
    @JsonIgnore
    private Voter voter;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    @JsonIgnore
    private Candidate candidate;

    @JsonProperty("voterId")
    public Long getVoterId() {
        return voter != null ? voter.getId() : null;
    }

    @JsonProperty("candidateId")
    public Long getCandidateId() {
        return candidate != null ? candidate.getId() : null;
    }

    @JsonProperty("candidateParty")
    public String getCandidateParty() {
        return candidate != null ? candidate.getParty() : null;
    }
}
