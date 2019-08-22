package com.purplehorse.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.purplehorse.notesapp.adapters.NotesAdapter;
import com.purplehorse.notesapp.models.Note;
import com.purplehorse.notesapp.persistance.NoteRepository;
import com.purplehorse.notesapp.util.NotesListDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.NoteClickListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton newNote;
    private TextView empty;

    private List<Note> notes = new ArrayList<>();
    private Note deletedNote;

    private NoteRepository noteRepository;
    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(viewHolder.getAdapterPosition());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.notes_list);
        newNote = findViewById(R.id.new_note);
        empty = findViewById(R.id.empty_text);

        newNote.setOnClickListener(this);

        noteRepository = new NoteRepository(this);
        noteRepository.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesList) {
                if (notes.size() > 0)
                    notes.clear();
                if (notesList != null)
                    notes.addAll(notesList);
                adapter.notifyDataSetChanged();
                if (notes.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
            }
        });

        adapter = new NotesAdapter(notes, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new NotesListDecoration(32));
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onNoteClicked(int position) {
        Log.d(TAG, "onNoteClicked: CLICKED!!!");
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("note", notes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void deleteNote(final int adapterPosition) {
        deletedNote = notes.get(adapterPosition);
        noteRepository.deleteNote(deletedNote);
        Snackbar.make(findViewById(R.id.main_container), "Deleted Note", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        noteRepository.insertNote(deletedNote);
                    }
                })
                .show();
    }
}
