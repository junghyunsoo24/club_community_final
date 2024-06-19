package web.term.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.term.club.domain.Enum.BoardType;
import web.term.club.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE (p.boardType = :boardType AND (p.boardType != 'NOTICE' OR p.open = TRUE)) ORDER BY p.id DESC")
    List<Post> findByBoardTypeOrderByIdDesc(@Param("boardType") BoardType boardType);
    List<Post> findTop5ByBoardTypeOrderByIdDesc(BoardType boardType);
    @Query("SELECT p FROM Post p WHERE (p.clubName IN :clubNames OR p.open = TRUE) AND p.boardType = 'NOTICE' ORDER BY p.id DESC")
    List<Post> findByClubNamesOrderByIdDesc(@Param("clubNames") List<String> clubNames);
}
