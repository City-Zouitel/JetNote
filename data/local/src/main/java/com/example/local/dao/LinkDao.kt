package com.example.local.dao

import androidx.room.*
import com.example.local.model.Link
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Query("select * from links_table")
    fun getAllLinks(): Flow<List<Link>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLink(link: Link)

    @Delete
    suspend fun deleteLink(link: Link)

}