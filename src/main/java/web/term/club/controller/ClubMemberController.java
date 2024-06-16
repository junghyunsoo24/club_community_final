package web.term.club.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.term.club.domain.Club;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Member;
import web.term.club.service.ClubMemberService;

import java.util.List;

@RestController
@RequestMapping("/clubMember")
public class ClubMemberController {
    @Autowired
    private ClubMemberService clubMemberService;

    //동아리 가입 신청페이지로 이동
    @GetMapping("/apply_club")
    public String signupPage() {
        return "apply_club";
    }
    //동아리 가입 신청(파일 업로드 및 요청)

    //승인 대기중인 동아리원 조회
    @GetMapping("/waiting/{clubId}")
    public ResponseEntity<?> getWaitingClubMembers(@PathVariable Long clubId, @RequestParam Long memberId) {
        try {
            Club club = new Club();
            club.setId(clubId);
            Member requestMember = new Member();
            requestMember.setId(memberId);
            List<ClubMember> clubMembers = clubMemberService.getWaitingClubMember(club, requestMember);
            if (clubMembers != null) {
                return new ResponseEntity<>(clubMembers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //해당 멤버 승인
    @PostMapping("/approve/{memberId}")
    public ResponseEntity<?> approveClubMember(@RequestParam Long requesterId, @RequestParam Long clubId, @PathVariable Long memberId) {
        try {
            Member requestMember = new Member();
            requestMember.setId(requesterId);
            ClubMember targetMember = new ClubMember();
            targetMember.setId(memberId);
            ClubMember updatedMember = clubMemberService.approveClubMember(requestMember, clubId, targetMember);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unauthorized access or error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 해당 멤버 거절
    @PostMapping("/reject/{memberId}")
    public ResponseEntity<?> rejectClubMember(@RequestParam Long requesterId, @RequestParam Long clubId, @PathVariable Long memberId) {
        try {
            Member requestMember = new Member();
            requestMember.setId(requesterId);
            ClubMember targetMember = new ClubMember();
            targetMember.setId(memberId);
            ClubMember updatedMember = clubMemberService.rejectClubMember(requestMember,clubId, targetMember);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unauthorized access or error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/ban/{memberId}")
    public ResponseEntity<?> banClubMember(@RequestParam Long requesterId, @RequestParam Long clubId, @PathVariable Long memberId) {
        try {
            Member requestMember = new Member();
            requestMember.setId(requesterId);
            ClubMember targetMember = new ClubMember();
            targetMember.setId(memberId);
            ClubMember updatedMember = clubMemberService.banClubMember(requestMember, clubId, targetMember);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unauthorized access or error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
