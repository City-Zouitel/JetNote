package com.example.jetnote.db.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.jetnote.db.entities.Entity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntityDao {

    @Transaction
    @Query("select * from NOTES where Trashed = 0 order by Date asc")
    fun allEntitiesOrderedById():Flow<List<Entity>>

    @Transaction
    @Query("select * from NOTES where Trashed = 0 order by Date desc")
    fun allEntitiesOrderedByNewest():Flow<List<Entity>>

    @Transaction
    @Query("select * from NOTES where Trashed = 0 order by Date asc")
    fun allEntitiesOrderedByOldest():Flow<List<Entity>>

    @Transaction
    @Query("select * from NOTES where Trashed = 0 order by TITLE asc")
    fun allEntitiesOrderedByName():Flow<List<Entity>>

    @Transaction
    @Query("select * from NOTES where Trashed = 0 order by case " +
            "when Priority like 'URGENT' then 1 " +
            "when Priority like 'IMPORTANT' then 2 " +
            "when Priority like 'NORMAL' then 3 " +
            "when Priority like 'LOW' then 4 " +
            "when Priority like 'NON' then 5 " +
            "end")
    fun allEntitiesOrderedByPriority():Flow<List<Entity>>

    @Transaction
    @Query("select * from NOTES where Trashed = 1")
    fun allTrashedNotes(): Flow<List<Entity>>

    @Transaction
    @Query("select * from NOTES where Trashed = 0 order by Reminding desc")
    fun allRemindingNotes(): Flow<List<Entity>>

}