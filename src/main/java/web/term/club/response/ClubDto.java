package web.term.club.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import web.term.club.domain.*;
import web.term.club.domain.Enum.ClubApprovalStatus;
import web.term.club.domain.Enum.ClubType;

@Data
@Builder
@AllArgsConstructor
public class ClubDto {

    private Long id;

    private String name;

    private ClubType clubType;

    private ClubApprovalStatus clubApprovalStatus;

    private String refuseInfo;

    //private ClubInfo clubInfo;

    private Long reqStudentId;

    private Long reqProfessorId;

    public static ClubDto of(Club club) {
        return ClubDto.builder()
                .id(club.getId())
                .name(club.getName())
                .clubType(club.getClubType())
                .clubApprovalStatus(club.getStatus())
                .refuseInfo(club.getRefuseInfo())
                //.clubInfo(club.getClubInfo())
                .reqStudentId(club.getReqStudent().getId())
                .reqProfessorId(club.getReqProfessor().getId())
                .build();
    }

    public Club toEntity(Member reqStudent, Member reqProfessor) {
        return Club.builder()
                .name(this.name)
                .clubType(this.clubType)
                .status(this.clubApprovalStatus)
                .refuseInfo(this.refuseInfo)
                //.clubInfo(this.clubInfo)
                .reqStudent(reqStudent)
                .reqProfessor(reqProfessor)
                .build();
    }
}