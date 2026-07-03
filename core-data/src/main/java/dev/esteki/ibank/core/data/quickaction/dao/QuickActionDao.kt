package dev.esteki.ibank.core.data.quickaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.esteki.ibank.core.data.quickaction.entity.QuickActionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuickActionDao {
    @Query("SELECT * FROM quick_actions")
    fun observeAll(): Flow<List<QuickActionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(actions: List<QuickActionEntity>)
}
