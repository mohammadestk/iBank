package dev.esteki.ibank.core.data.message

import dev.esteki.ibank.core.data.db.mapper.toDomain
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.message.MessageRepository
import dev.esteki.ibank.core.domain.message.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRepositoryImpl(
    private val localDataSource: MessageLocalDataSource,
) : MessageRepository {

    override fun getMessages(): Flow<Result<List<Message>>> =
        localDataSource.observeMessages().map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }

    override fun getUnreadCount(): Flow<Result<Int>> =
        localDataSource.observeUnreadCount().map { count ->
            Result.Success(count)
        }
}
