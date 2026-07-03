package dev.esteki.ibank.core.data.message.datasource

import dev.esteki.ibank.core.data.message.dao.MessageDao
import dev.esteki.ibank.core.data.message.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

class MessageLocalDataSource(
    private val messageDao: MessageDao,
) {
    fun observeMessages(): Flow<List<MessageEntity>> = messageDao.observeAll()

    fun observeUnreadCount(): Flow<Int> = messageDao.observeUnreadCount()
}
