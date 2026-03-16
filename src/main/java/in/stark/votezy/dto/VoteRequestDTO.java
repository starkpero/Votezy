package in.stark.votezy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequestDTO {
    @NotNull(message = "voterId is required")
    Long voterId;

    @NotNull(message = "candidateId is required")
    Long candidateId;
}
