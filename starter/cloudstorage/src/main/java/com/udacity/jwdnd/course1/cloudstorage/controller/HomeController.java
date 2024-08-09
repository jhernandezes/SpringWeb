package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.domain.Credential;
import com.udacity.jwdnd.course1.cloudstorage.domain.File;
import com.udacity.jwdnd.course1.cloudstorage.domain.Note;
import com.udacity.jwdnd.course1.cloudstorage.domain.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
    private final CredentialService credentialService;
    private UserMapper userMapper;

    public HomeController(FileService fileService, UserMapper userMapper, NotesService notesService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.notesService = notesService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String home(Model model, Authentication authentication, Note noteUpload, Credential credentialUpload) {

        String username = authentication.getName();

        User user = this.userMapper.getUser(username);

        List<File> files = fileService.getAllFiles(user);
        List<Note> notes = notesService.getAllNotes(user);
        List<Credential> credentialList = credentialService.getAllCredentials(user);

        // Decrypt password for view
        for (Credential credential : credentialList) {
            credential.setPassword(credentialService.decryptPassword(credential));
        }

        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentialList", credentialList);

        model.addAttribute("noteUpload", noteUpload);
        model.addAttribute("credentialUpload", credentialUpload);

        return "home";
    }


}
