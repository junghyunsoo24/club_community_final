package web.term.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import web.term.club.domain.*;
import web.term.club.repository.ClubInfoRepository;
import web.term.club.repository.ClubRepository;
import web.term.club.repository.FilePropertyRepository;
import web.term.club.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilePropertyService {
    private final FileRepository fileRepository;
    private final FilePropertyRepository filePropertyRepository;
    private final FilePropertyUtil filePropertyUtil;
    private final ClubRepository clubRepository;
    private final ClubInfoRepository clubInfoRepository;
    public FilePropertyResponse storeFile(MultipartFile multipartFile) throws IOException {
        //파일 정보 추출(생성)
        FileProperty fileProperty = filePropertyUtil.createFileProperty(multipartFile);
        //파일 시스템에 저장
        fileRepository.save(fileProperty, multipartFile);
        //DB에 저장
        filePropertyRepository.save(fileProperty);

        return FilePropertyResponse.of(fileProperty);
    }
    public List<FilePropertyResponse> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        //파일 정보 추출(생성)
        List<FilePropertyResponse> storeFileResults = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFiles.isEmpty()){
                FileProperty fileProperty = filePropertyUtil.createFileProperty(multipartFile);
                //파일 시스템에 저장
                fileRepository.save(fileProperty, multipartFile);
                //DB에 저장
                filePropertyRepository.save(fileProperty);
                storeFileResults.add(FilePropertyResponse.of(fileProperty));
            }
        }
        return storeFileResults;
    }
    public FilePropertyResponse delete(Long id) {
        FileProperty fileProperty = filePropertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일을 찾을 수 없습니다."));

        fileRepository.delete(fileProperty);
        filePropertyRepository.delete(fileProperty);
        return FilePropertyResponse.of(fileProperty);
    }

    @Value("${file.root-path}")
    private String basePath;

    public Resource loadClubSignUpFile(Long clubId) throws Exception {
        Club club = clubRepository.findFirstById(clubId);
        ClubInfo clubInfo = clubInfoRepository.findById(club.getClubInfo().getId())
                .orElseThrow(() -> new RuntimeException("ClubInfo not found"));
        String filename = clubInfo.getClubSignUpFile();
        Path file = Paths.get(basePath + filename).normalize();
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("File not found");
        }
    }
}

