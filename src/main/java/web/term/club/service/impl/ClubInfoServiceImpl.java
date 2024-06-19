package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.domain.ClubMember;
import web.term.club.repository.ClubInfoRepository;
import web.term.club.repository.ClubMemberRepository;
import web.term.club.repository.ClubRepository;
import web.term.club.response.ClubInfoDto;
import web.term.club.service.ClubInfoService;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClubInfoServiceImpl implements ClubInfoService {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubInfoRepository clubInfoRepository;

    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Override
    public ClubInfoDto getClubInfo(Club club) throws Exception {
        Club targetClub = clubRepository.findById(club.getId()).orElseThrow(() -> new IllegalArgumentException("동아리 조회 실패 :getClubInfo"));
        ClubInfo clubInfo = clubInfoRepository.findById(targetClub.getClubInfo().getId()).orElseThrow(() -> new IllegalArgumentException("동아리 정보 조회 실패 :getClubInfo"));
        ClubInfoDto clubInfoDto = ClubInfoDto.of(clubInfo);
        clubInfoDto.setClubMembers(targetClub.getClubMembers());
        return clubInfoDto;
    }

    @Override
    @Transactional
    public ClubInfo updateClubInfo(ClubInfoDto newClubInfoDto) throws Exception {
        System.out.println("DTO create success");
        Long testId = newClubInfoDto.getId();
        System.out.println("testId = " + testId);
        Club targetClub = clubRepository.findById(testId).orElseThrow(() -> new Exception("Club not found"));
        System.out.println("targetClub find");

        ClubInfo newClubInfo = newClubInfoDto.toEntity(targetClub);
        clubInfoRepository.save(newClubInfo);

        targetClub.setName(newClubInfoDto.getName());
        targetClub.setClubInfo(newClubInfo);

        List<ClubMember> members = newClubInfoDto.getClubMembers();
        System.out.println("Received club members from DTO:");
        members.forEach(member -> System.out.println("ID: " + member.getId() + ", Condition: " + member.getCondition() + ", Rank: " + member.getRank()));

        members.forEach(dtoMember -> {
            ClubMember dbMember = clubMemberRepository.findById(dtoMember.getId())
                    .orElseThrow(() -> new RuntimeException("Member not found with ID: " + dtoMember.getId())); // 멤버가 없는 경우 예외 발생

            dbMember.setCondition(dtoMember.getCondition());
            dbMember.setRank(dtoMember.getRank());
            clubMemberRepository.save(dbMember);
        });
        targetClub.setClubMembers(members);
        clubRepository.save(targetClub);
        return newClubInfo;
    }

    @Override
    public void saveClubInfo(ClubInfo clubInfo) {
        clubInfoRepository.save(clubInfo);
    }

    @Override
    public Club findFirstByName(String name){
        System.out.println("haha"+name);
        return clubRepository.findFirstByName(name);
    }

    @Override
    public ClubInfoDto updateClubInfo(Long clubId, String clubName, String clubInfo) throws Exception {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("동아리를 찾을 수 없습니다."));

        // 클럽 이름 업데이트
        club.setName(clubName);

        // ClubInfo 업데이트
        ClubInfo clubInfoEntity = club.getClubInfo();
        if (clubInfoEntity == null) {
            clubInfoEntity = new ClubInfo();
            club.setClubInfo(clubInfoEntity);
        }
        clubInfoEntity.setInfo(clubInfo);

        clubInfoRepository.save(clubInfoEntity);
        clubRepository.save(club);

        return ClubInfoDto.of(clubInfoEntity);
    }
}
