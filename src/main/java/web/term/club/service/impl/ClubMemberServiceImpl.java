package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.term.club.domain.Club;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Enum.Condition;
import web.term.club.domain.Enum.Role;
import web.term.club.domain.Member;
import web.term.club.repository.ClubMemberRepository;
import web.term.club.repository.ClubRepository;
import web.term.club.repository.MemberRepository;
import web.term.club.response.ClubMemberDto;
import web.term.club.service.ClubMemberService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClubMemberServiceImpl implements ClubMemberService {

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Override
    public ClubMember getClubMember(Long id) throws Exception{
        ClubMember clubMember = clubMemberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(" :targetClub"));
        return clubMember;
    }

    public List<ClubMemberDto> convertToClubMemberDTOList(List<ClubMember> clubMembers) {
        return clubMembers.stream().map(cm -> new ClubMemberDto(
                cm.getId(),
                cm.getClub(),
                cm.getStudent(),
                cm.getStudent().getName(),
                cm.getCondition(),
                cm.getRank()
        )).collect(Collectors.toList());
    }

    @Override
    public List<ClubMember> getAllClubMember(Club club) throws Exception {
        Club targetClub = clubRepository.findById(club.getId()).orElseThrow(() -> new IllegalArgumentException(" :targetClub"));
        return  targetClub.getClubMembers();
    }

    @Override
    public List<ClubMember> getWaitingClubMember(Club club, Member requestMember) throws Exception {
        Member masteMember = memberRepository.findById(requestMember.getId()).orElseThrow(() -> new IllegalArgumentException("신청자 권한확인용 회원검출 :getWaitingClubMember"));
        if(masteMember.getRole() != Role.MASTER){
            //System.out.println("함정에 빠졌다.");
            return null;
        }
        List<ClubMember> clubMembers = clubMemberRepository.findByClubAndCondition(club, Condition.WAITING);
        //System.out.println("clubMembers.get(0) = " + clubMembers.get(0));
        return clubMembers;
    }

    @Override
    public ClubMember approveClubMember(Member requestMember, Long clubId, Member targetMember) throws Exception {
        Member masteMember = memberRepository.findById(requestMember.getId()).orElseThrow(() -> new IllegalArgumentException("신청자 권한확인용 회원검출 :approveClubMember"));
        Member approveMember = memberRepository.findById(targetMember.getId()).orElseThrow(() -> new IllegalArgumentException("신청자 권한확인용 회원검출 :approveClubMember"));
        if(masteMember.getRole() != Role.MASTER){
            //System.out.println("함정에 빠졌다.");
            return null;
        }
        Club targetClub = clubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("동아리 정보 조회 실패 :approveClubMember"));
        ClubMember clubMember = clubMemberRepository.findByClubAndStudent(targetClub, approveMember);
        clubMember.setCondition(Condition.BELONG);
        return clubMemberRepository.save(clubMember);
    }

    @Override
    public ClubMember rejectClubMember(Member requestMember, Long clubId, Member targetMember) throws Exception {
        Member masteMember = memberRepository.findById(requestMember.getId()).orElseThrow(() -> new IllegalArgumentException("신청자 권한확인용 회원검출 :rejectClubMember"));
        Member rejectMember = memberRepository.findById(targetMember.getId()).orElseThrow(() -> new IllegalArgumentException("신청자 권한확인용 회원검출 :approveClubMember"));
        if(masteMember.getRole() != Role.MASTER){
            //System.out.println("함정에 빠졌다.");
            return null;
        }
        Club targetClub = clubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("동아리 정보 조회 실패 :rejectClubMember"));
        ClubMember clubMember = clubMemberRepository.findByClubAndStudent(targetClub, rejectMember);
        clubMember.setCondition(Condition.REFUSE);
        return clubMemberRepository.save(clubMember);
    }

    @Override
    public ClubMember banClubMember(Member requestMember, Long clubId, Member banedMember) throws Exception {
        Member masteMember = memberRepository.findById(requestMember.getId()).orElseThrow(() -> new IllegalArgumentException("신청자 권한확인용 회원검출 :banClubMember"));
        Member banMember = memberRepository.findById(banedMember.getId()).orElseThrow(() -> new IllegalArgumentException("신청자 권한확인용 회원검출 :approveClubMember"));
        if(masteMember.getRole() != Role.MASTER){
            //System.out.println("함정에 빠졌다.");
            return null;
        }
        Club targetClub = clubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("동아리 정보 조회 실패 :banClubMember"));
        ClubMember clubMember = clubMemberRepository.findByClubAndStudent(targetClub, banMember);
        clubMember.setCondition(Condition.WITHDRAWAL);
        return clubMemberRepository.save(clubMember);
    }
}
