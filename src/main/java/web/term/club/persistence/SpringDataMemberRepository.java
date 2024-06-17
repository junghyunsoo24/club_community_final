package web.term.club.persistence;

import web.term.club.domain.Enum.Gender;
import web.term.club.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataMemberRepository extends JpaRepository<Member,Long> {
    List<Member> findByName(String Name); //쿼리 메소드
    Member findFirstByName(String Name);

    Member findByNameAndDataOfBirthAndGenderAndDepartmentAndPhoneNumAndEmail(String name, int dataOfBirth, Gender gender, String department, String phoneNum, String email);
}
