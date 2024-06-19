package web.term.club.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.term.club.domain.Enum.Gender;
import web.term.club.domain.Enum.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    private String name;
    private int dataOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String department;
    private String phoneNum;
    private String email;
    private Long uniqueId;

    @Enumerated(EnumType.STRING)
    private Role role;



    @Builder
    public Member(String name, int dataOfBirth, Gender gender, String department, String phoneNum, String email, Long uniqueId, Role role){
        this.name = name;
        this.dataOfBirth = dataOfBirth;
        this.gender = gender;
        this.department = department;
        this.phoneNum = phoneNum;
        this.email = email;
        this.uniqueId = uniqueId;

        if(role == null){
            this.role = Role.NORMAL;
        }
        else{
            this.role = role;
        }

    }

    //마스터 권한 부여
    public void makeMaster(){
        this.setRole(Role.MASTER);
    }
}