package web.term.club.service;

import lombok.RequiredArgsConstructor;
import web.term.club.domain.FileProperty;
import web.term.club.domain.FilePropertyResponse;
import web.term.club.domain.FilePropertyUtil;
import web.term.club.repository.FilePropertyRepository;
import web.term.club.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilePropertyService {
    private final FileRepository fileRepository;
    private final FilePropertyRepository filePropertyRepository;
    private final FilePropertyUtil filePropertyUtil;
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
}

