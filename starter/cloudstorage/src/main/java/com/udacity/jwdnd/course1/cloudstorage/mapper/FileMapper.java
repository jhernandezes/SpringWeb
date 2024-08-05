package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.domain.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFilesByUserId(Integer userid);

    @Delete("DELETE FROM FILES WHERE filename = #{filename}")
    int deleteFileByFileName(String filename);
}

