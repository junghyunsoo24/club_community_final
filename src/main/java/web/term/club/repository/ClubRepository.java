package web.term.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.term.club.domain.Club;
import web.term.club.domain.Member;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByReqStudent(Member reqStudent);
    Club findFirstByName(String name);
    Club findFirstById(Long id);

    Club findFirstByReqStudent(Member masterMember);
}
