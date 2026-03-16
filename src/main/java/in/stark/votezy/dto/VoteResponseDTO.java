package in.stark.votezy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record VoteResponseDTO(
        String message,
        boolean success,
        Long voterId,
        Long candidateId,
        String candidateParty
) {}