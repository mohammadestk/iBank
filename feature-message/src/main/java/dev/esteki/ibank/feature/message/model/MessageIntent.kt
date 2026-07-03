package dev.esteki.ibank.feature.message.model

import dev.esteki.ibank.core.domain.message.model.Message

sealed interface MessageIntent {
    data object LoadMessages : MessageIntent
    data class FilterSelected(val filter: MessageFilter) : MessageIntent
    data class MessageClicked(val message: Message) : MessageIntent
}
