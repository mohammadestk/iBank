package dev.esteki.ibank.feature.message.model

import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.message.model.Message

sealed interface MessageResult {
    data object Idle : MessageResult
    data object Loading : MessageResult
    data class Success(val messages: List<Message>, val unreadCount: Int) : MessageResult
    data class Failure(val error: AppError, val message: String) : MessageResult
}
