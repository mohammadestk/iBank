package dev.esteki.ibank.core.data.message.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val timestamp: String,
    val type: String,
    val isRead: Boolean,
)
