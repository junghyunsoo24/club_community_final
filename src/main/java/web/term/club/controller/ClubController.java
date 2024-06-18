package web.term.club.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.term.club.request.ClubAcceptRequest;
import web.term.club.response.ClubDto;
import web.term.club.service.ClubSerivce;

import java.util.List;

@RestController
public class ClubController {
    @Autowired
    private ClubSerivce clubSerivce;

    @PostMapping("/club")
    public ResponseEntity<?> createClub(@RequestBody ClubDto clubDto) throws Exception {
        try {
            ClubDto club = clubSerivce.addClub(clubDto);
            return new ResponseEntity<>(club, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/club/accept")
    public ResponseEntity<?> acceptClub(@RequestBody ClubAcceptRequest clubAcceptRequest) throws Exception {
        try {
            ClubDto club = clubSerivce.acceptClub(clubAcceptRequest);
            return new ResponseEntity<>(club, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/club/own")
    public ResponseEntity<?> getOwnClubs() throws Exception {
        try {
            List<ClubDto> clubs = clubSerivce.myOwnClubs();
            return new ResponseEntity<>(clubs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/club/chairman/{memberId}")
    public ResponseEntity<?> getChairmansClub(@PathVariable Long memberId) throws Exception {
        try {
            ClubDto club = clubSerivce.chairmansClub(memberId);
            return new ResponseEntity<>(club, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
