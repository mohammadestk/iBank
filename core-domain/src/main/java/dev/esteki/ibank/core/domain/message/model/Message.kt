package dev.esteki.ibank.core.domain.message.model

data class Message(
    val id: String,
    val title: String,
    val description: String,
    val timestamp: String,
    val type: MessageType,
    val isRead: Boolean,
)
