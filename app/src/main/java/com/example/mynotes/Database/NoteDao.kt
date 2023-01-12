package com.example.mynotes.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotes.models.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note : Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notes_table order by id ASC")
    fun getallnotes() : LiveData<List<Note>>

    @Query("UPDATE notes_table Set tittle=:tittle ,note =:note WHERE id =:id")
    suspend fun update(id: Int?,tittle : String?, note : String?)
}