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
        Member member = memberRepository.findFirstByName("Master");
        Member member2 = memberRepository.findFirstByName("Dummy");
        for (BoardType boardType : BoardType.values()) {
            IntStream.rangeClosed(1, 6).forEach(i -> {
                Post post = createDummyPost(member, boardType);
                memberRepository.save(member);
                postRepository.save(post);
            });
        }
    }

    private void initMember(){
        initMember("kim", 20000908, Gender.MALE, "computer software engineering",
                "010-1234-5678","20190826@kumoh.ac.kr", Role.NORMAL);
        initMember("lee1", 20001111, Gender.MALE, "Electronic Engineering",
                "010-7676-5432","7676@naver.com", Role.NORMAL);
        initMember("pro1", 20001112, Gender.FEMALE, "computer software engineering",
                "010-2623-5432","2623@naver.com", Role.PROFESSOR);
        initMember("pro2", 20001113, Gender.MALE, "Electronic Engineering",
                "010-9876-5432","9876@naver.com", Role.PROFESSOR);
        initMember("bak", 20001114, Gender.FEMALE, "computer software engineering",
                "010-1243-5678","1243@naver.com\"", Role.NORMAL);
        initMember("beak", 20001115, Gender.MALE, "computer software engineering",
                "010-1235-5678","1235@naver.com\"", Role.NORMAL);
        initMember("beak", 20001116, Gender.FEMALE, "computer software engineering",
                "010-1263-5678","1263@naver.com\"", Role.NORMAL);
        initMember("Master", 2000,Gender.MALE,"Computer Science","123-456-7890", "dummy@example.com",Role.MASTER); // createDummyMember()
        initMember("Dummy", 2000,Gender.MALE,"Computer Science","123-456-7890", "dummy@example.com",Role.NORMAL); // createDummyMember2()
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
        Member lee1 = memberRepository.findFirstByName("lee1");
        Member kim = memberRepository.findFirstByName("kim");
        Member pro1 = memberRepository.findFirstByName("pro1");
        Member pro2 = memberRepository.findFirstByName("pro2");
        initClub("AClub",ClubType.CENTRAL,lee1, pro1);
        initClub("BClub",ClubType.DEPARTMENT,kim,pro2);
    }

    private void initClub(String name, ClubType clubType, Member reqStudent, Member reqProfessor){
        Club club = Club.builder()
                .name(name)
                .clubType(clubType)
                .reqStudent(reqStudent)
                .reqProfessor(reqProfessor)
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
        Member lee1 = memberRepository.findFirstByName("lee1");
        Member kim = memberRepository.findFirstByName("kim");
        Club aclub = clubRepository.findFirstByName("AClub");
        Club bclub = clubRepository.findFirstByName("BClub");
        initClubMember(aclub,lee1, null);
        initClubMember(aclub,kim, Condition.WAITING);
        initClubMember(bclub,lee1, Condition.WAITING);
        initClubMember(bclub,kim, null);
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
