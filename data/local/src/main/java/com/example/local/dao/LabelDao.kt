package com.example.local.dao

import androidx.room.*
import com.example.local.model.Label
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {

    @Query("select * from LABELS_TABLE")
    fun getAllLabels(): Flow<List<Label>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLabel(label: Label)

    @Update
    suspend fun updateLabel(label: Label)

    @Delete
    suspend fun deleteLabel(label: Label)
}