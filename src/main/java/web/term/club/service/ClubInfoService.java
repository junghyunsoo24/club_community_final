package web.term.club.service;

import org.springframework.web.multipart.MultipartFile;
import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.response.ClubInfoDto;

public interface ClubInfoService {
    ClubInfoDto getClubInfo(Club club) throws Exception;
    ClubInfo updateClubInfo(ClubInfoDto newClubInfo) throws Exception;
    void saveClubInfo(ClubInfo clubInfo);
    Club findFirstByName(String name);
    ClubInfoDto updateClubInfo(Long clubId, String clubName, String clubInfo) throws Exception;

    Club findById(Long clubId);
}
