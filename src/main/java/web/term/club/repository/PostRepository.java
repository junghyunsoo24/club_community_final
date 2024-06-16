package web.term.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.term.club.domain.Enum.BoardType;
import web.term.club.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoardType(BoardType boardType);
    List<Post> findTop5ByBoardTypeOrderByIdDesc(BoardType boardType);
}
