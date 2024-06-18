package web.term.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.term.club.domain.Club;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Enum.Condition;
import web.term.club.domain.Enum.Rank;
import web.term.club.domain.Member;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    List<ClubMember> findByClub(Club club);

    List<ClubMember> findByClubAndCondition(Club club, Condition condition);

    ClubMember findFirstById(Long id);

    ClubMember findByIdAndClub(Long id, Club targetClub);

    ClubMember findByClubAndStudent(Club club, Member student);

    List<ClubMember> findByStudentAndCondition(Member student, Condition condition);

    ClubMember findFirstByStudentAndRank(Member student, Rank rank);
}
