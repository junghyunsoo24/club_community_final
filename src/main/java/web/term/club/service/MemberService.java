package web.term.club.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpSession;
import web.term.club.domain.Enum.Gender;
import web.term.club.domain.Enum.Role;
import web.term.club.domain.Member;
import web.term.club.persistence.SpringDataMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class MemberService {

    @Autowired
    private SpringDataMemberRepository memberRepository;

    public Member joinMember(String name, int dataOfBirth, Gender gender, String department,
                             String phoneNum, String email) {
        Member member = Member.builder()
                .name(name)
                .dataOfBirth(dataOfBirth)
                .gender(gender)
                .department(department)
                .phoneNum(phoneNum)
                .email(email)
                .role(Role.NORMAL)
                .build();
        return memberRepository.save(member);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member loginMember(String name, int dataOfBirth, Gender gender, String department, String phoneNum, String email) {
        // DB에서 회원 조회 (이름, 생년월일, 성별, 학과, 전화번호, 이메일 모두 일치하는 회원)
        Member member = memberRepository.findByNameAndDataOfBirthAndGenderAndDepartmentAndPhoneNumAndEmail(name, dataOfBirth, gender, department, phoneNum, email);

        if (member != null) {
            return member; // 회원이 존재하면 Member 객체 반환
        } else {
            return null; // 회원이 존재하지 않으면 null 반환
        }
    }

    public Member findMember(String name) {

        return memberRepository.findFirstByName(name);

    }

    public void getUserInfo(String access_token,HttpSession session) throws Exception {
        ArrayList<Object> list = new ArrayList<Object>();

        final String requestUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + access_token);

        BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line = "";
        String result = "";

        while ((line = bf.readLine()) != null) {
            result +=line;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

        //콘솔창 확인 후 필요한 정보 추출
        System.out.println("----------properties"+properties);
        System.out.println("----------kakao_account"+kakao_account);

        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
        Long id = element.getAsJsonObject().get("id").getAsLong();

        list.add(nickname);

        session.setAttribute("name", nickname);
    }

    public String getToken(String code) throws Exception {
        String access_Token = "";

        //EndPoint URL = API가 서버에서 자원에 접근할 수 있도록 하는 URL
        final String requestUrl = "https://kauth.kakao.com/oauth/token";

        //토큰을 요청할 URL 객체 생성
        URL url = new URL(requestUrl);

        //HTTP 연결 설정
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        //서버로 요청 보내기
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=f2885fad71791b437dbbbc28d1a48796");
        sb.append("&redirect_uri=http://localhost:8081/kakao");
        sb.append("&code=" + code);
        bw.write(sb.toString());
        bw.flush();

        //서버의 응답 데이터 가져옴
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line = "";
        String result = "";

        //result에 토큰이 포함된 응답데이터를 한줄씩 저장
        while ((line = br.readLine()) != null) {
            result += line;
        }

        //JSON 데이터를 파싱하기 위한 JsonParser
        JsonParser parser = new JsonParser();
        //값 추출을 위해 파싱한 데이터를 JsonElement로 변환
        JsonElement element = parser.parse(result);

        //element에서 access_token 값을 얻어옴
        access_Token = element.getAsJsonObject().get("access_token").getAsString();

        br.close();
        bw.close();

        return access_Token;
    }

    public String getTokens(String code) throws Exception {
        String access_Token = "";

        //EndPoint URL = API가 서버에서 자원에 접근할 수 있도록 하는 URL
        final String requestUrl = "https://kauth.kakao.com/oauth/token";

        //토큰을 요청할 URL 객체 생성
        URL url = new URL(requestUrl);

        //HTTP 연결 설정
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        //서버로 요청 보내기
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=f2885fad71791b437dbbbc28d1a48796");
        sb.append("&redirect_uri=http://localhost:8081/kakaos");
        sb.append("&code=" + code);
        bw.write(sb.toString());
        bw.flush();

        //서버의 응답 데이터 가져옴
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line = "";
        String result = "";

        //result에 토큰이 포함된 응답데이터를 한줄씩 저장
        while ((line = br.readLine()) != null) {
            result += line;
        }

        //JSON 데이터를 파싱하기 위한 JsonParser
        JsonParser parser = new JsonParser();
        //값 추출을 위해 파싱한 데이터를 JsonElement로 변환
        JsonElement element = parser.parse(result);

        //element에서 access_token 값을 얻어옴
        access_Token = element.getAsJsonObject().get("access_token").getAsString();

        br.close();
        bw.close();

        return access_Token;
    }

    public void getTokenInfo(String access_token,HttpSession session) throws Exception {
        ArrayList<Object> list = new ArrayList<Object>();

        final String requestUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + access_token);

        BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line = "";
        String result = "";

        while ((line = bf.readLine()) != null) {
            result +=line;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

        //콘솔창 확인 후 필요한 정보 추출
        System.out.println("----------properties"+properties);
        System.out.println("----------kakao_account"+kakao_account);

        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
        Long id = element.getAsJsonObject().get("id").getAsLong();

        list.add(nickname);

        session.setAttribute("name", nickname);
    }

}