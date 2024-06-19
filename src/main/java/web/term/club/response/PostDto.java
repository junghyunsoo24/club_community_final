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
    private String imageUrl;
    private BoardType boardType;
    private String title;
    private String content;
    private String url;
    private String clubName;
    private String studentName;
    private boolean open; // 동아리 공지 전체 공개 여부

    public static PostDto of(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .studentId(post.getMember().getId())
//                .imageUrl(Optional.ofNullable(post.getFileProperty()).map(FileProperty::getFilePath).orElse(null))
                .imageUrl(post.getFileUrl())
                .boardType(post.getBoardType())
                .title(post.getTitle())
                .content(post.getContent())
                .url(post.getUrl())
                .clubName(post.getClubName())
                .studentName(post.getStudentName())
                .open(post.isOpen())
                .build();
    }

    public Post toEntity(Member member, String fileUrl) {
        return Post.builder()
                .member(member)
//                .fileProperty(fileProperty)
                .fileUrl(fileUrl)
                .boardType(this.boardType)
                .title(this.title)
                .content(this.content)
                .url(this.url)
                .clubName(this.clubName)
                .studentName(member.getName())
                .open(this.open)
                .build();
    }

    public Post toEntity(Member member) {
        return Post.builder()
                .member(member)
                .boardType(this.boardType)
                .title(this.title)
                .content(this.content)
                .url(this.url)
                .clubName(this.clubName)
                .studentName(member.getName())
                .open(this.open)
                .build();
    }
}