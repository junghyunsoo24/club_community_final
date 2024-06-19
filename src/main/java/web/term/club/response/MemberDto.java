package web.term.club.response;

import lombok.Getter;
import lombok.Setter;
import web.term.club.domain.Enum.Gender;

@Getter
@Setter
public class MemberDto {
    private int birthDate;
    private String email;
    private Gender gender;
    private String major;
    private String name;
    private String phone;
    private String uniqueId;

}
