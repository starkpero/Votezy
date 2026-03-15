package in.stark.votezy.repository;

import in.stark.votezy.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter,Long> {
    boolean existsByEmail(String email);

}
