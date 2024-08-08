package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.domain.Note;
import com.udacity.jwdnd.course1.cloudstorage.domain.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final UserMapper userMapper;
    private final NotesService notesService;

    public NoteController(NotesService notesService, UserMapper userMapper) {
        this.notesService = notesService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getNotes(Model model, Authentication authentication) {

        String username = authentication.getName();
        User user = userMapper.getUser(username);

        List<Note> notes = notesService.getAllNotes(user);
        model.addAttribute("notes", notes);

        System.out.println(notes);

        return "home";

    }

    @PostMapping
    public String handleNoteUpload(Note noteUpload, Authentication authentication, Model model) {

        String username = authentication.getName();
        Integer userId = userMapper.getUser(username).getUserId();

        noteUpload.setUserId(userId);
        notesService.addNote(noteUpload);
        model.addAttribute("notes", noteUpload);

        return "redirect:/home";
    }
}
