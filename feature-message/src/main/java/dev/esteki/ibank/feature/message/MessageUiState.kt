package dev.esteki.ibank.feature.message

import androidx.compose.runtime.Stable
import dev.esteki.ibank.core.domain.message.model.Message

@Stable
data class MessageUiState(
    val result: MessageResult,
    val messages: List<Message>,
    val filteredMessages: List<Message>,
    val unreadCount: Int,
    val selectedFilter: MessageFilter,
)
