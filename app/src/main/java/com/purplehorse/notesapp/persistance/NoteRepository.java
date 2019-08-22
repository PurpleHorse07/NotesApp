package com.purplehorse.notesapp.persistance;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.purplehorse.notesapp.async.DeleteAsyncTask;
import com.purplehorse.notesapp.async.InsertAsyncTask;
import com.purplehorse.notesapp.async.UpdateAsyncTask;
import com.purplehorse.notesapp.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase noteDatabase;

    public NoteRepository(Context context) {
        this.noteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNote(Note note) {
        new InsertAsyncTask(noteDatabase.getNoteDao()).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateAsyncTask(noteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> getNotes() {
        return noteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note) {
        new DeleteAsyncTask(noteDatabase.getNoteDao()).execute(note);
    }


}
