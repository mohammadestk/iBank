package dev.esteki.ibank.feature.message.viewmodel

import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.message.model.Message
import dev.esteki.ibank.core.domain.message.model.MessageType
import dev.esteki.ibank.core.domain.message.usecase.GetMessagesUseCase
import dev.esteki.ibank.core.domain.message.usecase.MessageData
import dev.esteki.ibank.feature.message.model.MessageFilter
import dev.esteki.ibank.feature.message.model.MessageIntent
import dev.esteki.ibank.feature.message.model.MessageResult
import dev.esteki.ibank.feature.message.model.MessageUiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MessageViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getMessagesUseCase: GetMessagesUseCase

    private val messages = listOf(
        Message("1", "Payment", "You received $100", "2 min ago", MessageType.TRANSACTION, false),
        Message("2", "Alert", "New login", "1 hour ago", MessageType.SECURITY, true),
        Message("3", "Offer", "5% cashback", "Yesterday", MessageType.PROMOTION, true),
        Message("4", "System", "Maintenance", "2 days ago", MessageType.SYSTEM, true),
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMessagesUseCase = mockk()
        every { getMessagesUseCase() } returns flowOf(Result.Success(MessageData(messages, 1)))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = MessageViewModel(
        initialUiState = MessageUiState(
            result = MessageResult.Idle,
            messages = emptyList(),
            filteredMessages = emptyList(),
            unreadCount = 0,
            selectedFilter = MessageFilter.INBOX,
        ),
        getMessagesUseCase = getMessagesUseCase,
    )

    @Test
    fun `loadMessages succeeds with all messages`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(MessageResult.Success::class.java)
        assertThat(state.messages).hasSize(4)
        assertThat(state.unreadCount).isEqualTo(1)
    }

    @Test
    fun `loadMessages failure shows Failure state`() = runTest {
        every { getMessagesUseCase() } returns flowOf(Result.Failure(AppError.Network))

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(MessageResult.Failure::class.java)
        assertThat((state.result as MessageResult.Failure).error).isEqualTo(AppError.Network)
    }

    @Test
    fun `filter by ALERTS shows only security messages`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(MessageIntent.FilterSelected(MessageFilter.ALERTS))

        val state = viewModel.uiState.value
        assertThat(state.selectedFilter).isEqualTo(MessageFilter.ALERTS)
        assertThat(state.filteredMessages).hasSize(1)
        assertThat(state.filteredMessages[0].type).isEqualTo(MessageType.SECURITY)
    }

    @Test
    fun `filter by SUPPORT shows transaction system and promotion messages`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(MessageIntent.FilterSelected(MessageFilter.SUPPORT))

        val state = viewModel.uiState.value
        assertThat(state.selectedFilter).isEqualTo(MessageFilter.SUPPORT)
        assertThat(state.filteredMessages).hasSize(3)
    }

    @Test
    fun `filter by INBOX shows all messages`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(MessageIntent.FilterSelected(MessageFilter.INBOX))

        val state = viewModel.uiState.value
        assertThat(state.selectedFilter).isEqualTo(MessageFilter.INBOX)
        assertThat(state.filteredMessages).hasSize(4)
    }
}
