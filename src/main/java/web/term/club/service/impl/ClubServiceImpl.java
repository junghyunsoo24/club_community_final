package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.term.club.domain.Member;
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
    private MemberRepository memberRepository;

    @Autowired
    private ClubInfoRepository clubInfoRepository;

    @Override
    public ClubDto addClub(ClubDto clubDto) throws Exception {
        // chairman은 접속 중인 사람이어야 함
        Member chairman = memberRepository.findById(clubDto.getReqStudentId()).orElseThrow(() -> new IllegalArgumentException("회원 정보 찾을 수 없음. : 회장"));
        Member supervisor = memberRepository.findById(clubDto.getReqProfessorId()).orElseThrow(() -> new IllegalArgumentException("회원 정보 찾을 수 없음. : 교수"));
        clubDto.setClubApprovalStatus(ClubApprovalStatus.WAITING);
        Club club = clubDto.toEntity(chairman, supervisor);
        ClubInfo clubInfo = new ClubInfo(club, null, null, null);
        club.setClubInfo(clubInfo);
        club.setRefuseInfo(null);
        clubInfoRepository.save(clubInfo);
        clubRepository.save(club);
        return ClubDto.of(club);
    }

    @Override
    public ClubDto acceptClub(ClubDto clubDto) throws Exception {
        // 임시 멤버 정의
        Member master = memberRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
        Club club;
        if ( /*master.userType == UserType.마스터*/ 1 == 1) {
            club = clubRepository.findById(clubDto.getId()).orElseThrow(IllegalArgumentException::new);
            club.setStatus(clubDto.getClubApprovalStatus());
            if (clubDto.getClubApprovalStatus() == ClubApprovalStatus.REFUSE) {
                club.setRefuseInfo(clubDto.getRefuseInfo());
            }
            clubRepository.save(club);
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

        List<Club> clubs = clubRepository.findByReqStudent(user);
        List<ClubDto> clubDtos = clubs.stream().map(club -> ClubDto.of(club)).collect(Collectors.toList());
        return clubDtos;
    }
}
