package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.domain.File;
import com.udacity.jwdnd.course1.cloudstorage.domain.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int addFile(File file) {
        return fileMapper.addFile(new File(null , file.getFileName(), file.getContentType(), file.getFileSize(), file.getUserId(), file.getFiledata()));
    }

    public File getFile(String filename, Integer userId){
        return this.fileMapper.getFile(filename, userId);
    }

    public void deleteFile(String filename, Integer userId){
        fileMapper.deleteFileByFileName(filename, userId);
    }

    public List<File> getAllFiles(User user) {
        return fileMapper.getFilesByUserId(user.getUserId());
    }
}
