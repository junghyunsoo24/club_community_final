package web.term.club.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.term.club.request.ClubAcceptRequest;
import web.term.club.response.ClubDto;
import web.term.club.service.ClubService;

import java.util.List;

@RestController
public class ClubController {
    @Autowired
    private ClubService clubService;

    @PostMapping("/club")
    public ResponseEntity<?> createClub(@RequestBody ClubDto clubDto) throws Exception {
        try {
            ClubDto club = clubService.addClub(clubDto);
            return new ResponseEntity<>(club, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/clubs")
    public ResponseEntity<?> clubs() throws Exception {
        try {
            List<ClubDto> clubs = clubService.clubs();
            return new ResponseEntity<>(clubs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/club/accept")
    public ResponseEntity<?> acceptClub(@RequestBody ClubAcceptRequest clubAcceptRequest) throws Exception {
        try {
            ClubDto club = clubService.acceptClub(clubAcceptRequest);
            return new ResponseEntity<>(club, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/club/own/{memberId}")
    public ResponseEntity<?> getOwnClubs(@PathVariable Long memberId) throws Exception {
        try {
            List<ClubDto> clubs = clubService.myOwnClubs(memberId);
            return new ResponseEntity<>(clubs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/club/chairman/{memberId}")
    public ResponseEntity<?> getChairmansClub(@PathVariable Long memberId) throws Exception {
        try {
            ClubDto club = clubService.chairmansClub(memberId);
            return new ResponseEntity<>(club, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/clubs/chairman/{memberId}")
    public ResponseEntity<?> getChairmansAllClub(@PathVariable Long memberId) throws Exception {
        try {
            List<ClubDto> clubs = clubService.chairmansClubs(memberId);
            return new ResponseEntity<>(clubs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/club/chairman/wait/{memberId}")
    public ResponseEntity<?> getChairmansWaitClub(@PathVariable Long memberId) throws Exception {
        try {
            ClubDto club = clubService.chairmansWaitClub(memberId);
            return new ResponseEntity<>(club, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
