package web.term.club.controller;

import jakarta.servlet.http.HttpSession;

import web.term.club.domain.Enum.Gender;
import web.term.club.domain.Member;
import web.term.club.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    //(1) 회원 가입 버튼 선택
    @GetMapping("/select")
    public String select() {
        return "login/select";
    }

    //(2) 일반 회원가입
    @PostMapping("/select")
    public String handleSelectPost() {
        return "login/join";
    }
    @PostMapping("/join")
    public String joinMember(@RequestParam String name, @RequestParam int dataOfBirth, @RequestParam Gender gender, @RequestParam String department, @RequestParam String phoneNum, @RequestParam String email, HttpSession session) {
        Member member = memberService.joinMember(name, dataOfBirth, gender, department, phoneNum, email);
        session.setAttribute("id", member.getId());
        return "redirect:/members";
    }

    //(3) 카카오 회원가입
    @GetMapping("/kakaoButton")
    public String kakaoButton() {
        return "login/kakao";
    }
    @PostMapping("/kakaoPost")
    public String kakaoPost(@RequestParam int dataOfBirth, @RequestParam Gender gender, @RequestParam String department, @RequestParam String phoneNum, @RequestParam String email, HttpSession session) {
        session.setAttribute("dataOfBirth", dataOfBirth);
        session.setAttribute("gender", gender);
        session.setAttribute("department", department);
        session.setAttribute("phoneNum", phoneNum);
        session.setAttribute("email", email);
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + "f2885fad71791b437dbbbc28d1a48796");
        url.append("&redirect_uri=http://localhost:8080/kakao");
        url.append("&response_type=code");
        return "redirect:" + url;
    }
    @RequestMapping(value = "/kakao")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {
        String access_token = memberService.getToken(code);//code로 토큰 받음
        memberService.getUserInfo(access_token, session);
        String name = (String) session.getAttribute("name");
        int dataOfBirth = (int) session.getAttribute("dataOfBirth");
        Gender gender = (Gender) session.getAttribute("gender");
        String department = (String) session.getAttribute("department");
        String phoneNum = (String) session.getAttribute("phoneNum");
        String email = (String) session.getAttribute("email");
        Member member = memberService.joinMember(name, dataOfBirth, gender, department, phoneNum, email);
        session.setAttribute("id", member.getId());
        return "redirect:/members";
    }

    //(4) 로그인 화면
    @PostMapping("/loginButton")
    public String loginButton() {
        return "login/logins";
    }
    @PostMapping("/logins")
    public String loginMember(@RequestParam String name, @RequestParam int dataOfBirth, @RequestParam Gender gender, @RequestParam String department, @RequestParam String phoneNum, @RequestParam String email, HttpSession session) {
        Member member = memberService.loginMember(name, dataOfBirth, gender, department, phoneNum, email);
        if (member != null) { // 회원이 존재하는 경우
            session.setAttribute("id", member.getId());
            return "redirect:/members";
        } else { // 회원이 존재하지 않는 경우
            return "login/logins"; // 로그인 페이지로 다시 이동 (로그인 페이지 뷰 이름을 "login"으로 가정)
        }
    }

    //(5) 사용자 화면
    @GetMapping("/members")
    public String showMembers(Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("id");
        System.out.println(memberId);
        if (memberId == null) {
            return "redirect:/join";
        }
        Member member = memberService.getMemberById(memberId);
        model.addAttribute("member", member);
        return "login/members";
    }
}