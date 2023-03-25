package com.example.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.local.model.Entity

@Dao
interface WidgetEntityDao {
    @Transaction
    @Query("select * from NOTES where Trashed = 0 order by Date desc")
    fun allWidgetEntitiesOrderedById():List<Entity>
}