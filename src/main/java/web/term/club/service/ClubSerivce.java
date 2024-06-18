package web.term.club.service;

import web.term.club.request.ClubAcceptRequest;
import web.term.club.response.ClubDto;
import web.term.club.domain.Club;

import java.util.List;

public interface ClubSerivce {
    ClubDto addClub(ClubDto clubDto) throws Exception;

    ClubDto acceptClub(ClubAcceptRequest clubAcceptRequest) throws Exception;

    List<ClubDto> clubs() throws Exception;

    Club club(Long clubId) throws Exception;

    List<ClubDto> myOwnClubs(Long memberId) throws Exception;

    ClubDto chairmansClub(Long memberId) throws Exception;

    ClubDto getChairmansWaitClub(Long memberId) throws Exception;
}
