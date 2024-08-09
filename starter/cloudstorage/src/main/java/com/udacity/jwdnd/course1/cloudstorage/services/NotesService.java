package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.domain.Note;
import com.udacity.jwdnd.course1.cloudstorage.domain.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private final NoteMapper noteMapper;

    public NotesService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(User user) {
        return noteMapper.getNotesFromUser(user.getUserId());
    }

    public void deleteNote(String noteTitle) {
        noteMapper.deleteNoteByNoteTitle(noteTitle);
    }

    public void updateNote(Note note) {
        noteMapper.updateNotel(note);
    }

    public int addNote(Note note) {
        return noteMapper.addNote(note);
    }

}
