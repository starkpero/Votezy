package in.stark.votezy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class ElectionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Cannot be blank")
    private String electionName;
    private int totalVotes;

    @OneToOne
    @JoinColumn(name="winner_id")
    @JsonIgnore
    private Candidate winner;

    @JsonProperty("winnerId")
    public Long getWinnerId(){
        return winner!=null? winner.getId():null;
    }

    @JsonProperty("candidateName")
    public String getCandidateName(){
        return winner!=null? winner.getName():null;
    }

}
