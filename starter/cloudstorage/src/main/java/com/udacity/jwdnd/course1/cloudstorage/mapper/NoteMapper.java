package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.domain.Credential;
import com.udacity.jwdnd.course1.cloudstorage.domain.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotesFromUser(Integer userId);

    @Delete("DELETE FROM NOTES WHERE notetitle = #{noteTitle}")
    int deleteNoteByNoteTitle(String noteTitle);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE userId = #{userId}")
    int updateNotel(Note note);

}
