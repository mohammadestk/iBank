package dev.esteki.ibank.core.data.message.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.esteki.ibank.core.data.message.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun observeAll(): Flow<List<MessageEntity>>

    @Query("SELECT COUNT(*) FROM messages WHERE isRead = 0")
    fun observeUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(messages: List<MessageEntity>)
}
