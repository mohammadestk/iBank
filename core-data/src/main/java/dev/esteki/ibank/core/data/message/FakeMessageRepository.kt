package dev.esteki.ibank.core.data.message

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.message.MessageRepository
import dev.esteki.ibank.core.domain.model.Message
import dev.esteki.ibank.core.domain.model.MessageType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class FakeMessageRepository @Inject constructor() : MessageRepository {

    private val messages = listOf(
        Message(
            id = UUID.randomUUID().toString(),
            title = "Payment Received",
            description = "You received $4,200.00 from Acme Corp",
            timestamp = "2 min ago",
            type = MessageType.TRANSACTION,
            isRead = false,
        ),
        Message(
            id = UUID.randomUUID().toString(),
            title = "Security Alert",
            description = "New login detected from iPhone 15 Pro",
            timestamp = "1 hour ago",
            type = MessageType.SECURITY,
            isRead = false,
        ),
        Message(
            id = UUID.randomUUID().toString(),
            title = "Bill Payment Due",
            description = "Electric bill of $142.50 is due in 3 days",
            timestamp = "3 hours ago",
            type = MessageType.SYSTEM,
            isRead = true,
        ),
        Message(
            id = UUID.randomUUID().toString(),
            title = "Special Offer",
            description = "Get 5% cashback on all international transactions",
            timestamp = "Yesterday",
            type = MessageType.PROMOTION,
            isRead = true,
        ),
        Message(
            id = UUID.randomUUID().toString(),
            title = "Transfer Complete",
            description = "$500.00 transferred to savings account",
            timestamp = "2 days ago",
            type = MessageType.TRANSACTION,
            isRead = true,
        ),
    )

    override fun getMessages(): Flow<Result<List<Message>>> = flowOf(
        Result.Success(messages)
    )

    override fun getUnreadCount(): Flow<Result<Int>> = flowOf(
        Result.Success(messages.count { !it.isRead })
    )
}
