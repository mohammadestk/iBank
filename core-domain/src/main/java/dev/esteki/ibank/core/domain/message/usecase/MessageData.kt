package dev.esteki.ibank.core.domain.message.usecase

import dev.esteki.ibank.core.domain.message.model.Message

data class MessageData(
    val messages: List<Message>,
    val unreadCount: Int,
)
