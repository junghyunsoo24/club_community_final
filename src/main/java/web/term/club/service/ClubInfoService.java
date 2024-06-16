package web.term.club.service;

import web.term.club.domain.Club;
import web.term.club.domain.ClubInfo;
import web.term.club.response.ClubInfoDto;

public interface ClubInfoService {
    ClubInfoDto getClubInfo(Club club) throws Exception;
    ClubInfo updateClubInfo(ClubInfoDto newClubInfo) throws Exception;
}
