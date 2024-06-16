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
        ClubInfo clubInfo = clubInfoRepository.findByClub(club);
        ClubInfoDto clubInfoDto = ClubInfoDto.of(clubInfo);
        clubInfoDto.setClubMembers(clubRepository.findFirstByName(club.getName()).getClubMembers());
        return clubInfoDto;
    }

    @Override
    public ClubInfo updateClubInfo(ClubInfoDto newClubInfoDto) throws Exception {
        ClubInfo newClubInfo = newClubInfoDto.toEntity(newClubInfoDto.getClub());
        clubInfoRepository.save(newClubInfo);
        //name은 ClubInfo에 없어서 2번 처리해야함
        Club targetClub = clubRepository.findFirstById(newClubInfoDto.getId());
        targetClub.setName(newClubInfoDto.getName());
        targetClub.setClubInfo(newClubInfo);
        clubRepository.save(targetClub);
        return newClubInfo;
    }
}
