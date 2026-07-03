package dev.esteki.ibank.feature.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.common.toUserMessage
import dev.esteki.ibank.core.domain.message.model.Message
import dev.esteki.ibank.core.domain.message.model.MessageType
import dev.esteki.ibank.core.domain.message.usecase.GetMessagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val initialUiState: MessageUiState,
    private val getMessagesUseCase: GetMessagesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<MessageUiState> = _uiState.asStateFlow()

    init {
        onIntent(MessageIntent.LoadMessages)
    }

    fun onIntent(intent: MessageIntent) {
        when (intent) {
            is MessageIntent.LoadMessages -> loadMessages()
            is MessageIntent.FilterSelected -> selectFilter(intent.filter)
            is MessageIntent.MessageClicked -> handleMessageClick(intent.message)
        }
    }

    private fun loadMessages() {
        _uiState.update { it.copy(result = MessageResult.Loading) }

        getMessagesUseCase()
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        _uiState.update { state ->
                            val filtered = filterMessages(data.messages, state.selectedFilter)
                            state.copy(
                                result = MessageResult.Success(
                                    messages = data.messages,
                                    unreadCount = data.unreadCount,
                                ),
                                messages = data.messages,
                                filteredMessages = filtered,
                                unreadCount = data.unreadCount,
                            )
                        }
                    }
                    is Result.Failure -> {
                        _uiState.update {
                            it.copy(
                                result = MessageResult.Failure(
                                    error = result.error,
                                    message = result.error.toUserMessage(),
                                ),
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun selectFilter(filter: MessageFilter) {
        _uiState.update { state ->
            val filtered = filterMessages(state.messages, filter)
            state.copy(
                selectedFilter = filter,
                filteredMessages = filtered,
            )
        }
    }

    private fun filterMessages(messages: List<Message>, filter: MessageFilter): List<Message> {
        return when (filter) {
            MessageFilter.INBOX -> messages
            MessageFilter.ALERTS -> messages.filter {
                it.type == MessageType.SECURITY
            }
            MessageFilter.SUPPORT -> messages.filter {
                it.type == MessageType.TRANSACTION ||
                        it.type == MessageType.SYSTEM ||
                        it.type == MessageType.PROMOTION
            }
        }
    }

    private fun handleMessageClick(message: Message) {
        // TODO: Navigate to message detail
    }
}
