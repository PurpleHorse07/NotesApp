package com.purplehorse.notesapp.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.purplehorse.notesapp.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    Long[] insertNotes(Note... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Update
    int updateNotes(Note... notes);

    @Delete
    int deleteNotes(Note... notes);
}
