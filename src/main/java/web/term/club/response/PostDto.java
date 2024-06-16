package web.term.club.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import web.term.club.domain.Enum.BoardType;
import web.term.club.domain.Member;
import web.term.club.domain.Post;

@Data
@Builder
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long studentId;
    private BoardType boardType;
    private String title;
    private String content;
    private String url;
    private boolean open; // 동아리 공지 전체 공개 여부

    public static PostDto of(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .studentId(post.getMember().getId())
                .boardType(post.getBoardType())
                .title(post.getTitle())
                .content(post.getContent())
                .url(post.getUrl())
                .open(post.isOpen())
                .build();
    }

    public Post toEntity(Member member) {
        return Post.builder()
                .member(member)
                .boardType(this.boardType)
                .title(this.title)
                .content(this.content)
                .url(this.url)
                .open(this.open)
                .build();
    }
}