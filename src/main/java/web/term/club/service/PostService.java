package web.term.club.service;

import org.springframework.web.multipart.MultipartFile;
import web.term.club.response.PostDto;
import web.term.club.domain.Enum.BoardType;

import java.util.List;

public interface PostService {
    PostDto addPost(PostDto postDTO) throws Exception;
    PostDto addPost(PostDto postDTO, MultipartFile imageFile) throws Exception;

    List<PostDto> getPostsByBoardType(BoardType boardType) throws Exception;
    List<PostDto> get5PostsByBoardType(BoardType boardType) throws Exception;
    List<PostDto> getNoticePostsByMemberId(Long memberId) throws Exception;
    List<PostDto> get5NoticePostsByMemberId(Long memberId) throws Exception;
}
