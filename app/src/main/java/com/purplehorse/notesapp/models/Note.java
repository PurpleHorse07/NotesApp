package com.purplehorse.notesapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(tableName = "notes")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String content;

    @NonNull
    private Long timestamp;

}
