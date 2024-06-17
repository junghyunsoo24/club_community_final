package web.term.club.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.term.club.domain.Club;
import web.term.club.response.ClubInfoDto;
import web.term.club.service.ClubInfoService;

@RestController
@RequestMapping("/clubInfo")
public class ClubInfoController {
    @Autowired
    private ClubInfoService clubInfoService;

    // 클럽 정보 조회
    // 검증 o
    //http://localhost:8081/clubInfo/1
    @GetMapping("/{clubId}")
    public ResponseEntity<?> getClubInfo(@PathVariable Long clubId) {
        try {
            Club club = new Club();
            club.setId(clubId);
            ClubInfoDto clubInfoDto = clubInfoService.getClubInfo(club);
            return new ResponseEntity<>(clubInfoDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // 클럽 정보 업데이트
    //
    @PostMapping("/update")
    public ResponseEntity<?> updateClubInfo(@RequestBody ClubInfoDto clubInfoDto) {
        try {
            ClubInfoDto updatedClubInfo = ClubInfoDto.of(clubInfoService.updateClubInfo(clubInfoDto));
            return new ResponseEntity<>(updatedClubInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
