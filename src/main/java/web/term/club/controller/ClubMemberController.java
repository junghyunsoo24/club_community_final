package web.term.club.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.term.club.domain.Club;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Enum.Role;
import web.term.club.domain.Member;
import web.term.club.response.ClubMemberDto;
import web.term.club.response.waitingClubMemberDto;
import web.term.club.service.ClubMemberService;

import java.util.List;

@RestController
@RequestMapping(value="/clubMember", method = {RequestMethod.GET, RequestMethod.POST})
public class ClubMemberController {
    @Autowired
    private ClubMemberService clubMemberService;

    //동아리 가입 신청페이지로 이동
    @GetMapping("/apply_club")
    public String signupPage() {
        return "apply_club";
    }
    //동아리 가입 신청(파일 업로드 및 요청)

    //동아리원 조회
    // 검증 o
    //http://localhost:8081/clubMember/1
    @GetMapping("/{clubId}")
    public ResponseEntity<?> getClubMembers(@PathVariable Long clubId) {
        try {
            Club club = new Club();
            club.setId(clubId);
            List<ClubMember> clubMembers = clubMemberService.getAllClubMember(club);
            List<ClubMemberDto> ClubMemberDto = clubMemberService.convertToClubMemberDTOList(clubMembers);
            if (clubMembers != null) {
                return new ResponseEntity<>(ClubMemberDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("null exception", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //승인 대기중인 동아리원 조회
    // 검증 o
    //http://localhost:8081/clubMember/waiting/1?memberId=1
    @GetMapping("/waiting/{clubId}")
    public ResponseEntity<?> getWaitingClubMembers(@PathVariable Long clubId, @RequestParam Long memberId) {
        try {
            Club club = new Club();
            club.setId(clubId);
            Member requestMember = new Member();
            requestMember.setId(memberId);
            List<waitingClubMemberDto> clubMemberDtos = clubMemberService.getWaitingClubMember(club, requestMember);

            if (clubMemberDtos != null) {
                return new ResponseEntity<>(clubMemberDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/files/{targetMemberId}")
    public ResponseEntity<?> getApplyClubFile(@PathVariable Long targetMemberId, @RequestParam Long requestMemberId) {
        try {
            ClubMember clubMember = new ClubMember();
            clubMember.setId(targetMemberId);

            Resource file = clubMemberService.getApplyClubFile(clubMember, requestMemberId);

            if (file != null) {
                return new ResponseEntity<>(file, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //해당 멤버 승인
    // 검증 o
    //http://localhost:8081/clubMember/approve/1?requesterId=1&clubId=1
    @PostMapping("/approve/{clubMemberId}")
    public ResponseEntity<?> approveClubMember(@RequestParam Long requesterId, @PathVariable Long clubMemberId) {
        try {
            ClubMember clubMember = clubMemberService.getClubMember(clubMemberId);
            Long clubId = clubMember.getClub().getId();
            Long memberId = clubMember.getStudent().getId();
            Member requestMember = new Member();
            requestMember.setId(requesterId);
            Member targetMember = new Member();
            targetMember.setId(memberId);
            ClubMember updatedMember = clubMemberService.approveClubMember(requestMember, clubId, targetMember);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unauthorized access or error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 해당 멤버 거절
    //검증 o
    //http://localhost:8081/clubMember/reject/1?requesterId=2&clubId=1
    @PostMapping("/reject/{clubMemberId}")
    public ResponseEntity<?> rejectClubMember(@RequestParam Long requesterId, @PathVariable Long clubMemberId) {
        try {
            ClubMember clubMember = clubMemberService.getClubMember(clubMemberId);
            Long clubId = clubMember.getClub().getId();
            Long memberId = clubMember.getStudent().getId();
            Member requestMember = new Member();
            requestMember.setId(requesterId);
            Member targetMember = new Member();
            targetMember.setId(memberId);
            ClubMember updatedMember = clubMemberService.rejectClubMember(requestMember,clubId, targetMember);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 해당 멤버 밴
    //검증 o
    //http://localhost:8081/clubMember/ban/1?requesterId=1&clubId=2
    @PostMapping("/ban/{clubMemberId}")
    public ResponseEntity<?> banClubMember(@RequestParam Long requesterId, @PathVariable Long clubMemberId) {
        try {
            ClubMember clubMember = clubMemberService.getClubMember(clubMemberId);
            Long clubId = clubMember.getClub().getId();
            Long memberId = clubMember.getStudent().getId();
            Member requestMember = new Member();
            requestMember.setId(requesterId);
            Member targetMember = new Member();
            targetMember.setId(memberId);
            ClubMember updatedMember = clubMemberService.banClubMember(requestMember, clubId, targetMember);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   //해당 유저 직급 변경
    @PostMapping("/changeRank/{targetclubMemberId}")
    public ResponseEntity<?> changeRankClubMember(@RequestParam Long requesterId, @RequestParam String rank, @PathVariable Long targetclubMemberId) {
        try {
            ClubMember changedClubMember = clubMemberService.changeRank(requesterId, rank, targetclubMemberId);
            return new ResponseEntity<>(changedClubMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
