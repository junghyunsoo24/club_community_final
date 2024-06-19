package web.term.club.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.term.club.domain.Enum.Gender;
import web.term.club.domain.Member;
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

//    //(1) 회원 가입 버튼 선택
//    @GetMapping("/select")
//    public String select() {
//        return "login/select";
//    }
//
//    //(2) 일반 회원가입
//    @PostMapping("/select")
//    public String handleSelectPost() {
//        return "login/join";
//    }
//    @PostMapping("/join")
//    public String joinMember(@RequestParam String name, @RequestParam int dataOfBirth, @RequestParam Gender gender, @RequestParam String department, @RequestParam String phoneNum, @RequestParam String email, HttpSession session) {
//        Member member = memberService.joinMember(name, dataOfBirth, gender, department, phoneNum, email);
//        session.setAttribute("id", member.getId());
//        return "redirect:/members";
//    }

    //(3) 카카오 회원가입
//    @GetMapping("/kakaoRegisterButton")
//    public String kakaoButton() {
//        return "login/kakao";
//    }
//    @PostMapping("/kakaoPost")
//    public String kakaoPost(@RequestParam int dataOfBirth, @RequestParam Gender gender, @RequestParam String department, @RequestParam String phoneNum, @RequestParam String email, HttpSession session) {
//        session.setAttribute("dataOfBirth", dataOfBirth);
//        session.setAttribute("gender", gender);
//        session.setAttribute("department", department);
//        session.setAttribute("phoneNum", phoneNum);
//        session.setAttribute("email", email);
//        StringBuffer url = new StringBuffer();
//        url.append("https://kauth.kakao.com/oauth/authorize?");
//        url.append("client_id=" + "f2885fad71791b437dbbbc28d1a48796");
//        url.append("&redirect_uri=http://localhost:3000/kakao");
//        url.append("&response_type=code");
//        return "redirect:" + url;
//    }
//    @RequestMapping(value = "/kakao")
//    public String kakaoLogin(@RequestParam("code") String code, HttpSession session,  HttpServletResponse response) throws Exception {
//        String access_token = memberService.getToken(code);//code로 토큰 받음
//
//        memberService.getUserInfo(access_token, session);
//        String name = (String) session.getAttribute("name");
//        int dataOfBirth = (int) session.getAttribute("dataOfBirth");
//        Gender gender = (Gender) session.getAttribute("gender");
//        String department = (String) session.getAttribute("department");
//        String phoneNum = (String) session.getAttribute("phoneNum");
//        String email = (String) session.getAttribute("email");
//        Member member = memberService.joinMember(name, dataOfBirth, gender, department, phoneNum, email);
//        session.setAttribute("id", member.getId());
//        String redirectUrl = "http://localhost:3000";
//        response.sendRedirect(redirectUrl);
//
//        // 컨트롤러 메서드는 더 이상 뷰 이름을 반환하지 않음
//        return null;
//
//    }
//
//    //(4) 로그인 화면
//    @GetMapping("/loginButton")
//    public String loginButton() {
//        StringBuffer url = new StringBuffer();
//        url.append("https://kauth.kakao.com/oauth/authorize?");
//        url.append("client_id=" + "f2885fad71791b437dbbbc808128d1a48796");
//        url.append("&redirect_uri=http://localhost:/kakaos");
//        url.append("&response_type=code");
//
//        return "redirect:" + url.toString();
//    }
//    @RequestMapping(value = "/kakaos")
//    public String loginNext(@RequestParam("code") String code, HttpSession session , HttpServletResponse response) throws Exception {
//        String access_token = memberService.getTokens(code);//code로 토큰 받음
//
//        memberService.getUserInfo(access_token, session);
//
//        String name = (String) session.getAttribute("name");
//
//        Member member = memberService.findMember(name);
//
//        if (member != null) { // 회원이 존재하는 경우
//            session.setAttribute("id", member.getId());
//
//            String redirectUrl = "http://localhost:3000?access_token=" + access_token;
//            response.sendRedirect(redirectUrl);
//
//            return null;
//        } else { // 회원이 존재하지 않는 경우
//            return "login/logins"; // 로그인 페이지로 다시 이동 (로그인 페이지 뷰 이름을 "login"으로 가정)
//        }
//    }
//    @PostMapping("/loginButton")
//    public String kakaoLogin(@RequestParam int dataOfBirth, @RequestParam Gender gender, @RequestParam String department, @RequestParam String phoneNum, @RequestParam String email, HttpSession session) {
//        session.setAttribute("dataOfBirth", dataOfBirth);
//        session.setAttribute("gender", gender);
//        session.setAttribute("department", department);
//        session.setAttribute("phoneNum", phoneNum);
//        session.setAttribute("email", email);
//        StringBuffer url = new StringBuffer();
//        url.append("https://kauth.kakao.com/oauth/authorize?");
//        url.append("client_id=" + "f2885fad71791b437dbbbc28d1a48796");
//        url.append("&redirect_uri=http://localhost:3000/kakao");
//        url.append("&response_type=code");
//        return "redirect:" + url;
//    }
//    @PostMapping("/logins")
//    public String loginMember(@RequestParam String name, @RequestParam int dataOfBirth, @RequestParam Gender gender, @RequestParam String department, @RequestParam String phoneNum, @RequestParam String email, HttpSession session) {
//        Member member = memberService.loginMember(name, dataOfBirth, gender, department, phoneNum, email);
//        if (member != null) { // 회원이 존재하는 경우
//            session.setAttribute("id", member.getId());
//            return "redirect:/members";
//        } else { // 회원이 존재하지 않는 경우
//            return "login/logins"; // 로그인 페이지로 다시 이동 (로그인 페이지 뷰 이름을 "login"으로 가정)
//        }
//    }

    //(5) 사용자 화면
//    @GetMapping("/members")
//    public String showMembers(Model model, HttpSession session) {
//        Long memberId = (Long) session.getAttribute("id");
//        System.out.println(memberId);
//        if (memberId == null) {
//            return "redirect:/join";
//        }
//        Member member = memberService.getMemberById(memberId);
//        model.addAttribute("member", member);
//        return "login/members";
//    }

//    @GetMapping("/user")
//    public ResponseEntity<Map<String, Object>> showAll(HttpServletRequest request, HttpSession session) throws Exception {
//        String token = request.getHeader("Authorization");
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//
//            memberService.getTokenInfo(token, session);
//
//            String name = (String) session.getAttribute("name");
//            Member member = memberService.findMember(name);
//            if (member == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 회원 정보가 없을 경우 401 Unauthorized
//            }
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("id", member.getId());
//            response.put("name", member.getName());
//            response.put("dateOfBirth", member.getDataOfBirth());
//            response.put("gender", member.getGender());
//            response.put("department", member.getDepartment());
//            response.put("phoneNum", member.getPhoneNum());
//            response.put("email", member.getEmail());
//            response.put("role", member.getRole());
//
//            return ResponseEntity.ok(response); // JSON 형태로 회원 정보 반환
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 토큰이 유효하지 않을 경우 401 Unauthorized
//        }
//    }

    @PostMapping("/join")
    public void joinMember(@RequestParam int birthDate, @RequestParam String email, @RequestParam Gender gender, @RequestParam String major, @RequestParam String name, @RequestParam String phone, @RequestParam String uniqueId) {
        Member member = memberService.joinMember(name, birthDate, gender, major, phone, email, uniqueId);
    }

    @GetMapping("/login/{uniqueId}")
    public ResponseEntity<Map<String, Object>> loginMember(@RequestParam String uniqueId) {
        Member member = memberService.findUniqueId(uniqueId);
        if (member != null) { // 회원이 존재하는 경우
            Map<String, Object> response = new HashMap<>();
            response.put("birthDate", member.getDataOfBirth());
            response.put("email", member.getEmail());
            response.put("gender", member.getGender());
            response.put("major", member.getDepartment());
            response.put("name", member.getName());
            response.put("phone", member.getPhoneNum());
            response.put("studentId", member.getId());
            response.put("uniqueId", member.getUniqueId());
            response.put("role", member.getRole());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 회원 정보가 없을 경우 401 Unauthorized
        }
    }


}