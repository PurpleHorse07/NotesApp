package com.purplehorse.notesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purplehorse.notesapp.R;
import com.purplehorse.notesapp.models.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private static final String TAG = "NotesAdapter";

    private List<Note> notes;

    private NoteClickListener listener;


    public NotesAdapter(List<Note> notes, NoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes_list, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getTitle());
        holder.time.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(new Date(notes.get(position).getTimestamp())));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public interface NoteClickListener {
        void onNoteClicked(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, time;
        NoteClickListener listener;

        ViewHolder(@NonNull View itemView, NoteClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onNoteClicked(getAdapterPosition());
        }
    }
}
