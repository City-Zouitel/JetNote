package com.example.local.daos

import androidx.room.*
import com.example.local.model.Label
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {

    @Query("select * from label")
    fun getAllLabels(): Flow<List<Label>>

    @Insert
    suspend fun addLabel(label: Label)

    @Update
    suspend fun updateLabel(label: Label)

    @Delete
    suspend fun deleteLabel(label: Label)
}