package web.term.club.response;

import lombok.*;

import java.io.File;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
public class waitingClubMemberDto {
    private Long id;
    private String name;
    private String department;
    private String filePath;


}
