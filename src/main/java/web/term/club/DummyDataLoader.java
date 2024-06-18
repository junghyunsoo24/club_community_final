package web.term.club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.term.club.domain.Enum.*;
import web.term.club.domain.*;
import web.term.club.repository.*;

import java.time.LocalTime;
import java.util.stream.IntStream;
@Transactional
@Component
public class DummyDataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ClubInfoRepository clubInfoRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Autowired
    public DummyDataLoader(MemberRepository memberRepository, PostRepository postRepository,
                           ClubInfoRepository clubInfoRepository, ClubRepository clubRepository,
                           ClubMemberRepository clubMemberRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.clubInfoRepository =clubInfoRepository;
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        deleteAllData();
        generateDummyData();
    }

    private void deleteAllData() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
        clubRepository.deleteAll();
        clubMemberRepository.deleteAll();
        clubInfoRepository.deleteAll();
    }

    private void generateDummyData() {
        initMember();
        initClub();
        initClubInfo();
        initClubMember();
        initPost();
    }

    private void initPost(){
        Member member = memberRepository.findFirstByName("이회장");
        Member member2 = memberRepository.findFirstByName("김더미");
        for (BoardType boardType : BoardType.values()) {
            IntStream.rangeClosed(1, 6).forEach(i -> {
                Post post = createDummyPost(member, boardType);
                postRepository.save(post);
            });
        }
    }

    private void initMember(){
        initMember("김회장", 20000908, Gender.MALE, "computer software engineering",
                "010-1234-5678","20190826@kumoh.ac.kr", Role.MASTER);
        initMember("이진철", 20001111, Gender.MALE, "Electronic Engineering",
                "010-7676-5432","7676@naver.com", Role.NORMAL);
        initMember("김교수", 20001112, Gender.FEMALE, "computer software engineering",
                "010-2623-5432","2623@naver.com", Role.PROFESSOR);
        initMember("이교수", 20001113, Gender.MALE, "Electronic Engineering",
                "010-9876-5432","9876@naver.com", Role.PROFESSOR);
        initMember("스팀팩", 20001114, Gender.FEMALE, "computer software engineering",
                "010-1243-5678","1243@naver.com", Role.NORMAL);
        initMember("트런들", 20001115, Gender.MALE, "computer software engineering",
                "010-1235-5678","1235@naver.com", Role.NORMAL);
        initMember("르블랑", 20001116, Gender.FEMALE, "computer software engineering",
                "010-1263-5678","1263@naver.com", Role.NORMAL);
        initMember("이회장", 2000,Gender.MALE,"Computer Science","123-456-7890", "dummy@example.com",Role.MASTER); // createDummyMember()
        initMember("김더미", 2000,Gender.MALE,"Computer Science","123-456-7890", "dummy@example.com",Role.NORMAL); // createDummyMember2()
        initMember("김운희", 19921212, Gender.FEMALE, "computer software engineering",
                "010-1212-3232","11231423@naver.com", Role.NORMAL);
        initMember("박사장", 19921212, Gender.FEMALE, "computer software engineering",
                "010-0000-1111","k1k1@naver.com", Role.MANAGER);
        initMember("홍길동", 19921122,Gender.MALE,"Computer Science","123-456-7890", "dummy1@example.com",Role.NORMAL);
        initMember("손흥민", 19931202,Gender.MALE,"Computer Science","123-456-7891", "dummy2@example.com",Role.NORMAL);
        initMember("박지성", 19940719,Gender.MALE,"Computer Science","123-456-7892", "dummy3@example.com",Role.NORMAL);
        initMember("이상혁", 19950915,Gender.MALE,"Computer Science","123-456-7893", "dummy4@example.com",Role.NORMAL);
        initMember("김연아", 19961011,Gender.FEMALE,"Computer Science","123-456-7893", "dummy5@example.com",Role.NORMAL);
    }

    private void initMember(String name, int dataOfBirth, Gender gender, String department, String phoneNum, String email, Role role){
        Member member = Member.builder()
                .name(name)
                .dataOfBirth(dataOfBirth)
                .gender(gender)
                .department(department)
                .phoneNum(phoneNum)
                .email(email)
                .role(role)
                .build();
        memberRepository.save(member);
    }

    private Post createDummyPost(Member member, BoardType boardType) {
        return Post.builder()
                .member(member)
                .boardType(boardType)
                .title("Dummy Post - " + boardType)
                .content("This is a dummy post for " + boardType)
                .url("https://example.com")
                .build();
    }

    private void initClub(){
        Member lee1 = memberRepository.findFirstByName("이회장");
        Member kim = memberRepository.findFirstByName("김회장");
        Member pro1 = memberRepository.findFirstByName("김교수");
        Member pro2 = memberRepository.findFirstByName("이교수");
        initClub("AClub",ClubType.CENTRAL,lee1, pro1);
        initClub("BClub",ClubType.DEPARTMENT,kim,pro2);
    }

    private void initClub(String name, ClubType clubType, Member reqStudent, Member reqProfessor){
        Club club = Club.builder()
                .name(name)
                .clubType(clubType)
                .applicantName(reqStudent.getName())
                .applicantDepartment(reqStudent.getDepartment())
                .applicantContact(reqStudent.getPhoneNum())
                .professorName(reqProfessor.getName())
                .professorDepartment(reqProfessor.getDepartment())
                .professorContact(reqProfessor.getPhoneNum())
                .status(ClubApprovalStatus.ACTIVE)
                .build();
        Member clubMaster = memberRepository.findById(reqStudent.getId()).orElseThrow(() -> new IllegalArgumentException("동아리 창설 학생 조회 실패 :initClub"));;
        clubMaster.setRole(Role.MASTER);
        memberRepository.save(clubMaster);
        clubRepository.save(club);
    }

    private void initClubInfo(){
        Club aclub = clubRepository.findFirstByName("AClub");
        Club bclub = clubRepository.findFirstByName("BClub");

        initClubInfo(aclub, "중앙동아리","image-path", LocalTime.of(10,0));
        initClubInfo(bclub, "학과동아리","image-path", LocalTime.of(14,0));

    }
    private void initClubInfo(Club club, String info, String img, LocalTime meetingTime){
        ClubInfo clubInfo = ClubInfo.builder()
                .club(club)
                .info(info)
                .img(img)
                .meetingTime(meetingTime)
                .build();
        club.setClubInfo(clubInfo);
        clubInfoRepository.save(clubInfo);
    }

    private void initClubMember(){
        Member lee1 = memberRepository.findFirstByName("이회장");
        Member kim = memberRepository.findFirstByName("김회장");
        Club aclub = clubRepository.findFirstByName("AClub");
        Club bclub = clubRepository.findFirstByName("BClub");
        initClubMember(aclub,lee1, null);
        initClubMember(aclub,kim, Condition.WAITING);
        initClubMember(bclub,lee1, Condition.WAITING);
        initClubMember(bclub,kim, null);

        Member a = memberRepository.findFirstByName("손흥민");
        initClubMember(aclub,a, Condition.BELONG);
        Member b = memberRepository.findFirstByName("김연아");
        initClubMember(aclub,b, Condition.WAITING);
        Member c = memberRepository.findFirstByName("박지성");
        initClubMember(aclub,c, Condition.WAITING);
        Member d = memberRepository.findFirstByName("이상혁");
        initClubMember(aclub,d, Condition.WAITING);
        Member e = memberRepository.findFirstByName("김연아");
        initClubMember(aclub,e, Condition.WAITING);
    }

    private void initClubMember(Club club, Member student, Condition condition ){
        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .student(student)
                .condition(condition)
                .build();
        club.getClubMembers().add(clubMember);
        clubMemberRepository.save(clubMember);
        clubRepository.save(club);
    }
}
