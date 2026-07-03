package dev.esteki.ibank.core.domain.message.usecase

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.message.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: MessageRepository,
) {
    operator fun invoke(): Flow<Result<MessageData>> = combine(
        repository.getMessages(),
        repository.getUnreadCount(),
    ) { messages, unreadCount ->
        val failures = listOf(messages, unreadCount)
            .filterIsInstance<Result.Failure>()

        if (failures.isNotEmpty()) {
            return@combine Result.Failure(failures.first().error)
        }

        val messagesData = (messages as Result.Success).data
        val unreadCountData = (unreadCount as Result.Success).data

        Result.Success(
            MessageData(
                messages = messagesData,
                unreadCount = unreadCountData,
            )
        )
    }
}
