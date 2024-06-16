package web.term.club.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ClubInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String info;

    @Column
    private LocalTime meetingTime;

    @Column(columnDefinition = "TEXT")
    private String img;

    @OneToOne(mappedBy = "clubInfo")
    private Club club;

    private String clubSignUpFile;

    @Builder
    public ClubInfo(Club club,String info, String img,LocalTime meetingTime){
        this.club = club;
        this.info = info;
        this.img = img;
        this.meetingTime = meetingTime;
        this.clubSignUpFile = club.getName() + ".txt";
    }
}