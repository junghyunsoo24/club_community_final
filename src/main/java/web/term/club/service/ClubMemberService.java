package web.term.club.service;

import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Member;

import java.util.List;

public interface ClubMemberService {
    List<ClubMember> getAllClubMember(Club club, Member requestMember) throws Exception;
    //ClubMember applyClubMember(Club club, Member requestMember, )
    List<ClubMember> getWaitingClubMember(Club club,Member requestMember)throws Exception;
    ClubMember approveClubMember(Member requestMember,Long clubid ,ClubMember targetMember) throws Exception;
    ClubMember rejectClubMember(Member requestMember, Long clubid ,ClubMember targetMember) throws Exception;

    ClubMember banClubMember(Member requestMember, Long clubid ,ClubMember banedMember) throws Exception;

}
