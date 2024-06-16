package web.term.club.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ApplyClubForm {
    private String name;
    private String studentId;
    private String department;
    private MultipartFile attachedFile;
}

