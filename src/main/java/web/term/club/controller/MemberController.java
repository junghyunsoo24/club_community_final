package web.term.club.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.term.club.domain.Enum.Gender;
import web.term.club.domain.Member;
import web.term.club.response.MemberDto;
import web.term.club.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/member", method = {RequestMethod.GET, RequestMethod.POST})
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody MemberDto memberDto) {
        Member member = memberService.joinMember(
                memberDto.getName(),
                memberDto.getBirthDate(),
                memberDto.getGender(),
                memberDto.getMajor(),
                memberDto.getPhone(),
                memberDto.getEmail(),
                memberDto.getUniqueId()
        );

        return ResponseEntity.ok("");
    }

    @GetMapping("/login/{uniqueId}")
    public ResponseEntity<Map<String, Object>> loginMember(@PathVariable Long uniqueId) throws Exception {
        Member member = memberService.findUniqueId(uniqueId);
        if (member != null) { // 회원이 존재하는 경우
            System.out.println(member.getUniqueId());

            Map<String, Object> response = new HashMap<>();
            response.put("id", member.getId());
            response.put("name", member.getName());
            response.put("dateOfBirth", member.getDataOfBirth());
            response.put("gender", member.getGender());
            response.put("department", member.getDepartment());
            response.put("phoneNum", member.getPhoneNum());
            response.put("email", member.getEmail());
            response.put("uniqueId", member.getUniqueId());
            response.put("role", member.getRole());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 회원 정보가 없을 경우 401 Unauthorized
        }
    }

}