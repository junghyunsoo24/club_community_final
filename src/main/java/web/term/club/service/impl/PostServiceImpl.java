package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.term.club.domain.Enum.Role;
import web.term.club.response.PostDto;
import web.term.club.domain.Enum.BoardType;
import web.term.club.domain.Member;
import web.term.club.domain.Post;
import web.term.club.repository.MemberRepository;
import web.term.club.repository.PostRepository;
import web.term.club.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    public PostDto addPost(PostDto postDto) throws IllegalAccessException {
        Member member = memberRepository.findById(postDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (member.getRole() != Role.MASTER) {
            throw new IllegalAccessException("마스터 권한이 필요합니다.");
        }

        Post post = postDto.toEntity(member);

        postRepository.save(post);

        return PostDto.of(post);
    }

    @Override
    public List<PostDto> getPostsByBoardType(BoardType boardType) throws Exception {
        List<Post> posts = postRepository.findByBoardType(boardType);

        if (posts.isEmpty()) {
            throw new RuntimeException("해당하는 게시물이 없습니다.");
        }

        return posts.stream()
                .map(PostDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> get5PostsByBoardType(BoardType boardType) {
        List<Post> posts = postRepository.findTop5ByBoardTypeOrderByIdDesc(boardType);

        if (posts.isEmpty()) {
            throw new RuntimeException("해당하는 게시물이 없습니다.");
        }

        return posts.stream()
                .map(PostDto::of)
                .collect(Collectors.toList());
    }
}
