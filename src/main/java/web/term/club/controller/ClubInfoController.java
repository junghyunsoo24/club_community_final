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
    //실험용 body
    /*
    {
  "id": 1,
  "name": "CClub",
  "info": "This is a description of the Example Club.",
  "img": "aaaaa",
  "meetingTime": "18:00:00",
  "clubSignUpFile": "http://example.com/signup_form.pdf",
  "club": {
    "id": 1,
    "name": "AClub",
    "clubType": "CENTRAL",
    "status": "ACTIVE",
    "refuseInfo": "",
    "reqStudent": {
      "id": 2,
      "student_id": "2"
    },
    "reqProfessor": {
      "id": 3,
      "student_id": "3"
    }
  },
"clubMembers" :[
  {
    "id": 1,
    "condition": "BELONG",
    "rank": "NORMAL"
  },
  {
    "id": 2,
    "condition": "BELONG",
    "rank": "ADMINISTRATOR"
  }

]
}

     */

}
