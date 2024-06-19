package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Enum.Role;
import web.term.club.repository.ClubMemberRepository;
import web.term.club.response.PostDto;
import web.term.club.domain.Enum.BoardType;
import web.term.club.domain.Member;
import web.term.club.domain.Post;
import web.term.club.repository.MemberRepository;
import web.term.club.repository.PostRepository;
import web.term.club.service.FilePropertyService;
import web.term.club.service.PostService;
import web.term.club.domain.FilePropertyResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private FilePropertyService filePropertyService;

    @Override
    public PostDto addPost(PostDto postDTO) throws IllegalAccessException {
        Member member = memberRepository.findById(postDTO.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (member.getRole() != Role.MASTER) {
            throw new IllegalAccessException("마스터 권한이 필요합니다.");
        }
        Post post = postDTO.toEntity(member);

        postRepository.save(post);

        return PostDto.of(post);
    }

    @Override
    public PostDto addPost(PostDto postDTO, MultipartFile imageFile) throws IllegalAccessException, IOException {
        Member member = memberRepository.findById(postDTO.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (member.getRole() != Role.MASTER) {
            throw new IllegalAccessException("마스터 권한이 필요합니다.");
        }


        Post post;
        if (imageFile != null && !imageFile.isEmpty()) {
            FilePropertyResponse filePropertyResponse = filePropertyService.storeFile(imageFile);

            post = postDTO.toEntity(member, filePropertyResponse.getFileUrl());


            postRepository.save(post);
        } else {
            post = postDTO.toEntity(member);

            postRepository.save(post);
        }


        return PostDto.of(post);
    }

    @Override
    public List<PostDto> getPostsByBoardType(BoardType boardType) throws Exception {
        List<Post> posts = postRepository.findByBoardTypeOrderByIdDesc(boardType);

        if (posts.isEmpty()) {
            throw new RuntimeException("해당하는 게시물이 없습니다.");
        }

        return posts.stream()
                .map(PostDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> get5PostsByBoardType(BoardType boardType) {
        List<Post> posts = postRepository.findByBoardTypeOrderByIdDesc(boardType);

        if (posts.isEmpty()) {
            throw new RuntimeException("해당하는 게시물이 없습니다.");
        }

        return posts.stream()
                .limit(5) // 최대 5개의 게시물만 선택
                .map(PostDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getNoticePostsByMemberId(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        List<ClubMember> clubMembers = clubMemberRepository.findByStudent(member);

        List<String> clubNames = clubMembers.stream()
                .map(cm -> cm.getClub().getName())
                .collect(Collectors.toList());

        List<Post> posts = postRepository.findByClubNamesOrderByIdDesc(clubNames);

        return posts.stream()
                .map(PostDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> get5NoticePostsByMemberId(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        List<ClubMember> clubMembers = clubMemberRepository.findByStudent(member);

        List<String> clubNames = clubMembers.stream()
                .map(cm -> cm.getClub().getName())
                .collect(Collectors.toList());

        List<Post> posts = postRepository.findByClubNamesOrderByIdDesc(clubNames);

        return posts.stream()
                .limit(5) // 최대 5개의 게시물만 선택
                .map(PostDto::of)
                .collect(Collectors.toList());
    }
}
