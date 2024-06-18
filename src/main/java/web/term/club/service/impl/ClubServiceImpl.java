package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Enum.*;
import web.term.club.domain.Member;
import web.term.club.repository.ClubMemberRepository;
import web.term.club.repository.MemberRepository;
import web.term.club.request.ClubAcceptRequest;
import web.term.club.response.ClubDto;
import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.domain.Enum.ClubApprovalStatus;
import web.term.club.repository.ClubInfoRepository;
import web.term.club.repository.ClubRepository;
import web.term.club.service.ClubSerivce;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClubServiceImpl implements ClubSerivce {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubInfoRepository clubInfoRepository;

    @Override
    public ClubDto addClub(ClubDto clubDto) throws Exception {
        clubDto.setClubApprovalStatus(ClubApprovalStatus.WAITING);
        Club club = clubDto.toEntity();
        ClubInfo clubInfo = new ClubInfo(club, null, null, null);
        club.setClubInfo(clubInfo);
        club.setRefuseInfo(null);
        clubInfoRepository.save(clubInfo);
        clubRepository.save(club);
        return ClubDto.of(club);
    }

    @Override
    public ClubDto acceptClub(ClubAcceptRequest clubAcceptRequest) throws Exception {
        Member master = memberRepository.findById(clubAcceptRequest.getMemberId()).orElseThrow(IllegalArgumentException::new);
        Club club;
        if (master.getRole() == Role.MANAGER) {
            club = clubRepository.findById(clubAcceptRequest.getId()).orElseThrow(IllegalArgumentException::new);
            ClubApprovalStatus tempStatus = club.getStatus();
            club.setStatus(clubAcceptRequest.getClubApprovalStatus());
            if (clubAcceptRequest.getClubApprovalStatus() == ClubApprovalStatus.REFUSE) {
                club.setRefuseInfo(clubAcceptRequest.getRefuseInfo());
            }
            else if (tempStatus == ClubApprovalStatus.WAITING && clubAcceptRequest.getClubApprovalStatus() == ClubApprovalStatus.ACTIVE) {
                Member chairman = memberRepository.findFirstByName(club.getApplicantName());
                ClubMember clubMember = new ClubMember(club, chairman, Condition.BELONG, Rank.CHAIRMAN);
                chairman.setRole(Role.MASTER);
                clubRepository.save(club);
                clubMemberRepository.save(clubMember);
//                Member professor = memberRepository.findFirstByName(club.getProfessorName());
//                ClubMember clubProfessor = new ClubMember(club, professor, Condition.BELONG, Rank.ADMINISTRATOR);
//                clubMemberRepository.save(clubProfessor);
            }
            return ClubDto.of(club);
        }
        else {
            throw new IllegalArgumentException("권한이 없습니다. ");
        }
    }

    @Override
    public List<ClubDto> clubs() throws Exception {
        List<Club> clubs = clubRepository.findAll();
        List<ClubDto> clubDtos = clubs.stream().map(club -> ClubDto.of(club)).collect(Collectors.toList());
        return clubDtos;
    }

    @Override
    public Club club(Long clubId) throws Exception {
        Club club = clubRepository.findById(clubId).orElseThrow(IllegalArgumentException::new);
        return club;
    }

    @Override
    public List<ClubDto> myOwnClubs(Long memberId) throws Exception {
        // 임시 멤버 정의
        Member user = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        List<ClubMember> clubMembers = clubMemberRepository.findByStudentAndCondition(user, Condition.BELONG);
        List<ClubDto> clubDtos = clubMembers.stream().map(m -> ClubDto.of(m.getClub())).collect(Collectors.toList());
        return clubDtos;
    }

    @Override
    public ClubDto chairmansClub(Long memberId) throws Exception {
        // 임시 멤버 정의
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        ClubMember clubMember = clubMemberRepository.findFirstByStudentAndRank(member, Rank.CHAIRMAN);

        ClubDto clubDto = ClubDto.of(clubMember.getClub());
        return clubDto;
    }

    @Override
    public List<ClubDto> chairmansClubs(Long memberId) throws Exception {
        // 임시 멤버 정의
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        List<Club> clubs = clubRepository.findByApplicantName(member.getName());
        List<ClubDto> clubDtos = clubs.stream().map(c -> ClubDto.of(c)).collect(Collectors.toList());

        return clubDtos;
    }

    @Override
    public ClubDto chairmansWaitClub(Long memberId) throws Exception {
        // 임시 멤버 정의
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Club club = clubRepository.findByApplicantNameAndStatus(member.getName(), ClubApprovalStatus.WAITING);

        return ClubDto.of(club);
    }

    @Override
    public Club saveClub(Club club) {
        return clubRepository.save(club);
    }

}
