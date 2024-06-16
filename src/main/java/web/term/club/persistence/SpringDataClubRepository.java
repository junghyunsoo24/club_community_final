package web.term.club.persistence;

import web.term.club.domain.Club;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataClubRepository extends JpaRepository<Club, Long> {
    Club findFirstByName(String Name);

//    @Query("SELECT o FROM Order o join fetch o.member")
//    List<Order> findWithMemeberJPQL();
//
//    @EntityGraph(value = "order.member.graph", type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT o FROM Club o")
//    List<Club> findWithMemberGraph();

}
