package com.purplehorse.notesapp.async;

import android.os.AsyncTask;
import android.util.Log;

import com.purplehorse.notesapp.models.Note;
import com.purplehorse.notesapp.persistance.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    private static final String TAG = "UpdateAsyncTask";

    private NoteDao dao;

    public UpdateAsyncTask(NoteDao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: " + Thread.currentThread().getName());
        dao.updateNotes(notes);
        return null;
    }
}
