package dev.esteki.ibank.feature.message.model

import androidx.compose.runtime.Stable
import dev.esteki.ibank.core.domain.message.model.Message
import dev.esteki.ibank.feature.message.model.MessageFilter
import dev.esteki.ibank.feature.message.model.MessageResult

@Stable
data class MessageUiState(
    val result: MessageResult,
    val messages: List<Message>,
    val filteredMessages: List<Message>,
    val unreadCount: Int,
    val selectedFilter: MessageFilter,
)
