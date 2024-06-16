package web.term.club.service;

import web.term.club.response.PostDto;
import web.term.club.domain.Enum.BoardType;

import java.util.List;

public interface PostService {
    PostDto addPost(PostDto postDto) throws Exception;

    List<PostDto> getPostsByBoardType(BoardType boardType) throws Exception;
    List<PostDto> get5PostsByBoardType(BoardType boardType) throws Exception;
}
