package web.term.club.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.term.club.domain.Enum.Condition;
import web.term.club.domain.Enum.Rank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClubMember {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Member student;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    @Builder
    public ClubMember(Club club,  Member student) {
        this.club = club;
        this.student = student;
        this.condition = Condition.BELONG;
        this.rank = Rank.NORMAL;
    }
}