package web.term.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.term.club.domain.Club;
import web.term.club.domain.Member;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findFirstByName(String name);
    Club findFirstById(Long id);

}
