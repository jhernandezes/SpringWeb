package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.domain.File;
import com.udacity.jwdnd.course1.cloudstorage.domain.Note;
import com.udacity.jwdnd.course1.cloudstorage.domain.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final NotesService notesService;
    private UserMapper userMapper;

    public HomeController(FileService fileService, UserMapper userMapper, NotesService notesService) {
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.notesService = notesService;
    }

    @GetMapping
    public String home(Note noteUpload, Model model, Authentication authentication) {

        String username = authentication.getName();

        User user = this.userMapper.getUser(username);

        List<File> files = fileService.getAllFiles(user);
        List<Note> notes = notesService.getAllNotes(user);

        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("noteUpload", noteUpload);

        System.out.println("Total Files: " + files.size());
        System.out.println("Total Notes: " + files.size());


        return "home";
    }


}