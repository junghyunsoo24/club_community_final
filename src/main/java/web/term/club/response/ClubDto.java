package web.term.club.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.term.club.domain.*;
import web.term.club.domain.Enum.ClubApprovalStatus;
import web.term.club.domain.Enum.ClubType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto {

    private Long id;

    private String name;

    private ClubType clubType;

    private ClubApprovalStatus clubApprovalStatus;

    private String refuseInfo;

    private String applicantName;

    private String applicantDepartment;

    private String applicantContact;

    private String professorName;

    private String professorDepartment;

    private String professorContact;

    public static ClubDto of(Club club) {
        return ClubDto.builder()
                .id(club.getId())
                .name(club.getName())
                .clubType(club.getClubType())
                .clubApprovalStatus(club.getStatus())
                .refuseInfo(club.getRefuseInfo())
                .applicantName(club.getApplicantName())
                .applicantDepartment(club.getApplicantDepartment())
                .applicantContact(club.getApplicantContact())
                .professorName(club.getProfessorName())
                .professorDepartment(club.getProfessorDepartment())
                .professorContact(club.getProfessorContact())
                .build();
    }

    public Club toEntity() {
        return Club.builder()
                .name(this.name)
                .clubType(this.clubType)
                .status(this.clubApprovalStatus)
                .refuseInfo(this.refuseInfo)
                //.clubInfo(this.clubInfo)
                .applicantName(this.applicantName)
                .applicantDepartment(this.applicantDepartment)
                .applicantContact(this.applicantContact)
                .professorName(this.professorName)
                .professorDepartment(this.professorDepartment)
                .professorContact(this.professorContact)
                .build();
    }
}