package com.example.local.dao

import androidx.room.*
import com.example.local.model.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("select * from LABELS_TABLE")
    fun getAllTags(): Flow<List<TagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTag(tagEntity: TagEntity)

    @Update
    suspend fun updateTag(tagEntity: TagEntity)

    @Delete
    suspend fun deleteTag(tagEntity: TagEntity)
}