package dev.esteki.ibank.core.data.message

import kotlinx.coroutines.flow.Flow

class MessageLocalDataSource(
    private val messageDao: MessageDao,
) {
    fun observeMessages(): Flow<List<MessageEntity>> = messageDao.observeAll()

    fun observeUnreadCount(): Flow<Int> = messageDao.observeUnreadCount()
}
