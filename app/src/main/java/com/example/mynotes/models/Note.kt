package com.example.mynotes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)val id : Int?,
    @ColumnInfo(name = "tittle")val tittle : String?,
    @ColumnInfo(name = "note")val note : String?,
    @ColumnInfo(name = "date")val date : String?
) : java.io.Serializable