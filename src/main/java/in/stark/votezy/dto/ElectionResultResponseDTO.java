package in.stark.votezy.dto;

public record ElectionResultResponseDTO(
        String electionName,
        int totalVotes,
        Long winnerId,
        int winnerVotes,
        String winnerName
) {}
