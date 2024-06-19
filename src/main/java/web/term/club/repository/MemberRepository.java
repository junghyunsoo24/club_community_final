package web.term.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.term.club.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findFirstByName(String Name);

    Member findByUniqueId(String uniqueId);
}
