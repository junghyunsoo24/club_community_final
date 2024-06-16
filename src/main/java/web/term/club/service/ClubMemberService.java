package web.term.club.service;

import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Member;

import java.util.List;

public interface ClubMemberService {
    // 가입 신청서 반환
    // 가입 신청서 업로드 및 저장
    List<ClubMember> getAllClubMember(Club club, Member requestMember) throws Exception;

    List<ClubMember> getWaitingClubMember(Club club,Member requestMember)throws Exception;
    ClubMember approveClubMember(Member requestMember, ClubMember targetMember) throws Exception;
    ClubMember rejectClubMember(Member requestMember, ClubMember targetMember) throws Exception;

    ClubMember banClubMember(Member requestMember, ClubMember banedMember) throws Exception;

}
