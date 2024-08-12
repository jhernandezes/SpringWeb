package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.domain.Credential;
import com.udacity.jwdnd.course1.cloudstorage.domain.File;
import com.udacity.jwdnd.course1.cloudstorage.domain.Note;
import com.udacity.jwdnd.course1.cloudstorage.domain.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private UserMapper userMapper;

    public FileController(FileService fileService, UserMapper userMapper) {
        this.fileService = fileService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getFiles(Model model, Authentication authentication) {

        String username = authentication.getName();

        User user = this.userMapper.getUser(username);

        List<File> files = fileService.getAllFiles(user);

        model.addAttribute("files", files);

        model.addAttribute("noteUpload", new Note());
        model.addAttribute("credentialUpload", new Credential());

        System.out.println("Total Files: " + files.size());

        return "home";
    }

    @GetMapping("/delete/{fileName}")
    public String deleteFiles(@PathVariable("fileName") String fileName, Model model, Authentication authentication) {

        String username = authentication.getName();
        Integer userId = this.userMapper.getUser(username).getUserId();

        fileService.deleteFile(fileName, userId);
        return "redirect:/home";
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable("fileName") String fileName, Authentication authentication){

        String username = authentication.getName();
        Integer userId = this.userMapper.getUser(username).getUserId();

        File file = fileService.getFile(fileName, userId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }

    @PostMapping
    public String handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile, Model model)  {

        String username = authentication.getName();

        Integer userId = this.userMapper.getUser(username).getUserId();

        File file = new File();

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        long fileSize = multipartFile.getSize();
        byte[] fileData;
        try {
            fileData = multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (fileName != null) {
            if (fileService.getFile(fileName, userId) == null){
                file.setFileName(fileName);
                file.setFileSize(String.valueOf(fileSize));
                file.setContentType(contentType);
                file.setFiledata(fileData);
                file.setUserId(userId);

                this.fileService.addFile(file);
                model.addAttribute("files", file);
            }
        }

        return "redirect:/home";
    }
    
}
