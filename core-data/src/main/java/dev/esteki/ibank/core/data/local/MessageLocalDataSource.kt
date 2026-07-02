package dev.esteki.ibank.core.data.local

import dev.esteki.ibank.core.data.local.dao.MessageDao
import dev.esteki.ibank.core.data.local.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

class MessageLocalDataSource(
    private val messageDao: MessageDao,
) {
    fun observeMessages(): Flow<List<MessageEntity>> = messageDao.observeAll()

    fun observeUnreadCount(): Flow<Int> = messageDao.observeUnreadCount()
}
