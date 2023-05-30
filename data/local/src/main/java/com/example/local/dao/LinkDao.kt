package com.example.local.dao

import androidx.room.*
import com.example.local.model.LinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Query("select * from links_table")
    fun getAllLinks(): Flow<List<LinkEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLink(link: LinkEntity)

    @Delete
    suspend fun deleteLink(link: LinkEntity)

}