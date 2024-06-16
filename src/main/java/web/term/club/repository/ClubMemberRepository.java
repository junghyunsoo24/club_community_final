package web.term.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.term.club.domain.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
}
