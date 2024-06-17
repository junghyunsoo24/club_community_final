package web.term.club.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "student_id")
    private Member student;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    @Builder
    public ClubMember(Club club,  Member student, Condition condition) {
        this.club = club;
        this.student = student;
        if (condition == null){
            this.condition = Condition.BELONG;
        }
        else{
            this.condition = condition;
        }

        this.rank = Rank.NORMAL;
    }

    @Builder
    public ClubMember(Club club,  Member student, Condition condition, Rank rank) {
        this.club = club;
        this.student = student;
        if (condition == null){
            this.condition = Condition.BELONG;
        }
        else{
            this.condition = condition;
        }

        this.rank = rank;
    }
}