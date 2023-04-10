package com.example.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.local.model.Link

@Dao
interface LinkDao {

    @Insert
    suspend fun addLink(link: Link)

    @Delete
    suspend fun deleteLink(link: Link)

}