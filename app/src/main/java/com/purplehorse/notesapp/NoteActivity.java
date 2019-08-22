package com.purplehorse.notesapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

import com.purplehorse.notesapp.models.Note;
import com.purplehorse.notesapp.persistance.NoteRepository;

import java.util.Date;
import java.util.Objects;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText noteEdit, titleEdit;
    private TextView titleText;
    private AppCompatImageButton back, check;
    private Note note = null;
    private boolean editState = false, newNote = true;

    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteEdit = findViewById(R.id.note_edit);
        titleText = findViewById(R.id.title_text);
        titleEdit = findViewById(R.id.title_edit);
        back = findViewById(R.id.back);
        check = findViewById(R.id.check);

        titleText.setOnClickListener(this);
        back.setOnClickListener(this);
        check.setOnClickListener(this);

        noteRepository = new NoteRepository(this);


        if (getIntent().hasExtra("note")) {
            note = (Note) getIntent().getSerializableExtra("note");
            newNote = false;
        }

        if (note == null) {
            note = Note.builder().title("New Note").content("My note goes here...").timestamp(new Date().getTime()).build();
        }

        noteEdit.setText(note.getContent());
        titleText.setText(note.getTitle());
        titleEdit.setText(note.getTitle());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back: {
                setNote();
                finish();
                break;
            }
            case R.id.title_text: {
                editMode();
                break;
            }
            case R.id.check: {
                titleText.setText(titleEdit.getText());
                viewMode();
                break;
            }
        }
    }

    private void editMode() {
        editState = true;
        titleText.setVisibility(View.GONE);
        titleEdit.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        check.setVisibility(View.VISIBLE);
        titleEdit.requestFocus();
        titleEdit.selectAll();
    }

    @Override
    public void onBackPressed() {
        if (editState) {
            titleEdit.setText(titleText.getText());
            viewMode();
        } else {
            setNote();
            super.onBackPressed();
        }
    }

    private void setNote() {
        note.setContent(Objects.requireNonNull(noteEdit.getText()).toString());
        note.setTitle(titleText.getText().toString());
        note.setTimestamp(new Date().getTime());
        if (TextUtils.isEmpty(note.getContent().trim()) || TextUtils.isEmpty(note.getTitle().trim()))
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        else {
            if (newNote)
                noteRepository.insertNote(note);
            else
                noteRepository.updateNote(note);
        }
    }

    private void viewMode() {
        editState = false;
        titleText.setVisibility(View.VISIBLE);
        titleEdit.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        check.setVisibility(View.GONE);
    }
}
