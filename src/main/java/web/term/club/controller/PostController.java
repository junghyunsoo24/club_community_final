package web.term.club.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.term.club.response.PostDto;
import web.term.club.domain.Enum.BoardType;
import web.term.club.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    // 게시물 등록
    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto) throws Exception {
        try {
            PostDto registeredPost = postService.addPost(postDto);
            return new ResponseEntity<>(registeredPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    // 게시물 조회(게시판에 따라)
    @GetMapping("/board/{boardType}/all")
    public ResponseEntity<List<PostDto>> getAllPostsByBoardType(@PathVariable String boardType) {
        try {
            BoardType type = BoardType.valueOf(boardType.toUpperCase());
            List<PostDto> posts = postService.getPostsByBoardType(type);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 최근 5개 조회(게시판에 따라)
    @GetMapping("/board/{boardType}/latest")
    public ResponseEntity<List<PostDto>> getLatestPostsByBoardType(@PathVariable String boardType) {
        try {
            BoardType type = BoardType.valueOf(boardType.toUpperCase());
            List<PostDto> posts = postService.get5PostsByBoardType(type);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
