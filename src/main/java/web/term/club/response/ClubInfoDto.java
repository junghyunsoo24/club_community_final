package web.term.club.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Member;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ClubInfoDto {
    private Long id;
    private String name;
    private String info;
    private String img;
    private LocalTime meetingTime;
    private String clubSignUpFile;
    private List<ClubMember> clubMembers;

    @JsonIgnore
    @JsonProperty
    private Club club;

    public static ClubInfoDto of(ClubInfo clubInfo) {
        return ClubInfoDto.builder()
                .id(clubInfo.getId())
                .name(clubInfo.getClub().getName())
                .info(clubInfo.getInfo())
                .meetingTime(clubInfo.getMeetingTime())
                .img(clubInfo.getImg())
                .clubMembers(clubInfo.getClub().getClubMembers().stream().toList())
                .club(clubInfo.getClub())
                .clubSignUpFile(clubInfo.getClubSignUpFile())
                .build();
    }

    public ClubInfo toEntity(Club club) {
        return ClubInfo.builder()
                .club(club)
                .info(this.info)
                .img(this.img)
                .meetingTime(this.meetingTime)
                .build();
    }

}
