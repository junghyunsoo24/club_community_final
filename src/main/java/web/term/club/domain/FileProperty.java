package web.term.club.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FileProperty {

    @Id
    @GeneratedValue
    private Long id;
    private String uploadFileName;
    private String storedFileName;
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String contentType;
    private LocalDateTime uploadTime;
}

