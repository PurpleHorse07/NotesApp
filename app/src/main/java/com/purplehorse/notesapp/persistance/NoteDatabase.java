package com.purplehorse.notesapp.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.purplehorse.notesapp.models.Note;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public static final String DB_NAME = "notes_db";

    private static NoteDatabase instance;

    static NoteDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, DB_NAME)
                    .build();
        return instance;
    }

    public abstract NoteDao getNoteDao();
}
