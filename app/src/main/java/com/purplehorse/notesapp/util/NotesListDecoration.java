package com.purplehorse.notesapp.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesListDecoration extends RecyclerView.ItemDecoration {

    private int outerMargin;

    public NotesListDecoration(int outerMargin) {
        this.outerMargin = outerMargin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = outerMargin / 2;
        outRect.left = outerMargin;
        outRect.right = outerMargin;
        outRect.bottom = outerMargin / 2;
    }
}
