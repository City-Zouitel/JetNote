package com.example.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.local.model.relational.NoteEntity

@Dao
interface WidgetDao {
    @Transaction
    @Query("select * from NOTES_TABLE where Trashed = 0 order by Date desc")
    fun allWidgetEntitiesById():List<NoteEntity>
}