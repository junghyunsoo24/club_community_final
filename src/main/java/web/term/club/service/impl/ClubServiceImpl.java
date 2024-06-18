package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.term.club.domain.ClubMember;
import web.term.club.domain.Enum.Condition;
import web.term.club.domain.Enum.Rank;
import web.term.club.domain.Member;
import web.term.club.repository.ClubMemberRepository;
import web.term.club.repository.MemberRepository;
import web.term.club.response.ClubDto;
import web.term.club.domain.Enum.ClubApprovalStatus;
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
        // chairman은 접속 중인 사람이어야 함
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
    public ClubDto acceptClub(ClubDto clubDto) throws Exception {
        // 임시 관리자 멤버 정의
        Member master = memberRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
        Club club;
        if ( /*master.userType == UserType.마스터*/ 1 == 1) {
            club = clubRepository.findById(clubDto.getId()).orElseThrow(IllegalArgumentException::new);
            ClubApprovalStatus tempStatus = club.getStatus();
            club.setStatus(clubDto.getClubApprovalStatus());
            if (clubDto.getClubApprovalStatus() == ClubApprovalStatus.REFUSE) {
                club.setRefuseInfo(clubDto.getRefuseInfo());
            }
            else if (tempStatus == ClubApprovalStatus.WAITING && clubDto.getClubApprovalStatus() == ClubApprovalStatus.ACTIVE) {
                Member chairman = memberRepository.findFirstByName(club.getApplicantName());
                ClubMember clubMember = new ClubMember(club, chairman, Condition.BELONG, Rank.CHAIRMAN);
                clubRepository.save(club);
                clubMemberRepository.save(clubMember);
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
    public List<ClubDto> myOwnClubs() throws Exception {
        // 임시 멤버 정의
        Member user = memberRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
        List<ClubMember> clubMembers = clubMemberRepository.findByStudentAndCondition(user, Condition.BELONG);
        List<ClubDto> clubDtos = clubMembers.stream().map(m -> ClubDto.of(m.getClub())).collect(Collectors.toList());
        return clubDtos;
    }

    @Override
    public ClubDto chairmansClub(Long memberId) throws Exception {
        // 임시 멤버 정의
        Member user = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        ClubMember clubMember = clubMemberRepository.findFirstByStudentAndRank(user, Rank.CHAIRMAN);

        ClubDto clubDto = ClubDto.of(clubMember.getClub());
        return clubDto;
    }
}
