package web.term.club.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import web.term.club.domain.Enum.ClubApprovalStatus;
import web.term.club.domain.Enum.ClubType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Enumerated(EnumType.STRING)
    private ClubType clubType;

    @Enumerated(EnumType.STRING)
    private ClubApprovalStatus status;

    @Column(columnDefinition = "TEXT")
    private String refuseInfo;

    @Builder.Default
    @OneToMany
    private List<ClubMember> clubMembers = new ArrayList<>();;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "club_info_id")
    private ClubInfo clubInfo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String applicantName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String applicantDepartment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String applicantContact;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String professorName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String professorDepartment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String professorContact;

}