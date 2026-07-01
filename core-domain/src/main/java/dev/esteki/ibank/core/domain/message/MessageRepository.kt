package dev.esteki.ibank.core.domain.message

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(): Flow<Result<List<Message>>>
    fun getUnreadCount(): Flow<Result<Int>>
}
