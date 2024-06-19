package web.term.club.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import web.term.club.domain.Enum.BoardType;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id")
    private Member member;


    //    private FileProperty fileProperty;
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private String title;
    private String content;
    private String url;
    private String clubName;
    private String studentName;
    @Builder.Default
    private boolean open = false; // 동아리 공지 전체 공개 여부


//    @Lob
//    @Column(name = "image", columnDefinition = "BLOB")
//    private byte[] image;  // 이미지 데이터를 저장하는 필드
}
