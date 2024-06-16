package web.term.club.controller;

import lombok.RequiredArgsConstructor;
import web.term.club.domain.FilePropertyResponse;
import web.term.club.response.ApiResponse;
import web.term.club.response.ResponseCode;
import web.term.club.service.FilePropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileTestController {
    private final FilePropertyService filePropertyService;

    @GetMapping
    public String fileUpload(){
        return "file-upload";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping
    public ApiResponse<FilePropertyResponse> uploadFile(@ModelAttribute online.shop.controller.ShopCreateForm form) throws IOException {
        MultipartFile attachedFile = form.getAttachedFile();
        List<MultipartFile> imageFiles = form.getImageFiles();
        //null처리와 response 하나로 병합 필요
        FilePropertyResponse filePropertyResponse = filePropertyService.storeFile(attachedFile);
        List<FilePropertyResponse> filePropertyResponses = filePropertyService.storeFiles(imageFiles);
        return ApiResponse.response(ResponseCode.Created, filePropertyResponse);
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public ApiResponse<FilePropertyResponse> deleteFile(@PathVariable Long id){
        return ApiResponse.response(ResponseCode.OK, filePropertyService.delete(id));
    }
}

