package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.domain.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Controller
@RequestMapping("/upload")
public class FileController {

    private final FileService fileService;
    private UserMapper userMapper;

    public FileController(FileService fileService, UserMapper userMapper) {
        this.fileService = fileService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public String handleFileUpload(Authentication authentication, @RequestParam("file") MultipartFile multipartFile) {

        String username = authentication.getName();

        Integer userId = this.userMapper.getUser(username).getUserId();

        File file = new File();

        try {

            String fileName = multipartFile.getOriginalFilename();
            String contentType = multipartFile.getContentType();
            long fileSize = multipartFile.getSize();
            Blob fileData = new SerialBlob(multipartFile.getBytes());


            file.setFileName(fileName);
            file.setFileSize(String.valueOf(fileSize));
            file.setContentType(contentType);
            file.setBlob(fileData);
            file.setUserId(userId);

        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.fileService.addFile(file);

        return "result";
    }
}
