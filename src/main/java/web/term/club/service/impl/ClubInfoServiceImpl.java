package web.term.club.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.repository.ClubInfoRepository;
import web.term.club.repository.ClubRepository;
import web.term.club.response.ClubInfoDto;
import web.term.club.service.ClubInfoService;

@Service
@Transactional
public class ClubInfoServiceImpl implements ClubInfoService {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubInfoRepository clubInfoRepository;

    @Override
    public ClubInfoDto getClubInfo(Club club) throws Exception {
        ClubInfo clubInfo = clubInfoRepository.findById(club.getId()).orElseThrow(() -> new IllegalArgumentException("동아리 정보 조회 실패 :getClubInfo"));
        ClubInfoDto clubInfoDto = ClubInfoDto.of(clubInfo);
        clubInfoDto.setClubMembers(clubRepository.findById(club.getId())
                .orElseThrow(() -> new IllegalArgumentException("동아리 정보 조회 실패 :getClubInfo2"))
                .getClubMembers());
        return clubInfoDto;
    }

    @Override
    public ClubInfo updateClubInfo(ClubInfoDto newClubInfoDto) throws Exception {
        Club targetClub = clubRepository.findById(newClubInfoDto.getClub().getId())
                .orElseThrow(() -> new Exception("Club not found"));

        ClubInfo newClubInfo = newClubInfoDto.toEntity(targetClub);
        clubInfoRepository.save(newClubInfo);

        targetClub.setName(newClubInfoDto.getName());
        targetClub.setClubInfo(newClubInfo);
        targetClub.setClubMembers(newClubInfoDto.getClubMembers());
        clubRepository.save(targetClub);
        return newClubInfo;
    }
}
