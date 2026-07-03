package dev.esteki.ibank.core.domain.message.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.message.model.Message
import dev.esteki.ibank.core.domain.message.model.MessageType
import dev.esteki.ibank.core.domain.message.repository.MessageRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMessagesUseCaseTest {

    private lateinit var repository: MessageRepository
    private lateinit var useCase: GetMessagesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetMessagesUseCase(repository)
    }

    @Test
    fun `invoke combines messages and unread count`() = runTest {
        // Arrange
        val messages = listOf(
            Message("1", "Payment", "Desc", "2 min ago", MessageType.TRANSACTION, false),
            Message("2", "Alert", "Desc", "1 hour ago", MessageType.SECURITY, true),
        )
        every { repository.getMessages() } returns flowOf(Result.Success(messages))
        every { repository.getUnreadCount() } returns flowOf(Result.Success(1))

        // Act & Assert
        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val data = (result as Result.Success).data
            assertThat(data.messages).hasSize(2)
            assertThat(data.unreadCount).isEqualTo(1)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns failure when messages repository fails`() = runTest {
        every { repository.getMessages() } returns flowOf(Result.Failure(AppError.Network))
        every { repository.getUnreadCount() } returns flowOf(Result.Success(0))

        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.Network)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns failure when unread count repository fails`() = runTest {
        every { repository.getMessages() } returns flowOf(Result.Success(emptyList()))
        every { repository.getUnreadCount() } returns flowOf(Result.Failure(AppError.Timeout))

        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.Timeout)
            awaitComplete()
        }
    }
}
