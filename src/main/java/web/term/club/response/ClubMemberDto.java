package web.term.club.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import web.term.club.domain.Club;
import web.term.club.domain.Enum.Condition;
import web.term.club.domain.Enum.Rank;
import web.term.club.domain.Member;

@Data
@Builder
@AllArgsConstructor
public class ClubMemberDto {

    private long id;
    private String name;
    @JsonIgnore
    private Club club;
    @JsonIgnore
    private Member student;

    private Condition condition;

    private Rank rank;

    public ClubMemberDto(Long id, Club club, Member student, String name, Condition condition, Rank rank) {
        this.id = id;
        this.club = club;
        this.student = student;
        this.name = name;
        this.condition = condition;
        this.rank = rank;
    }
}
