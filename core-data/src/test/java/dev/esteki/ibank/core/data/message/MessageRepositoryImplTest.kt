package dev.esteki.ibank.core.data.message

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.message.MessageType
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MessageRepositoryImplTest {

    private lateinit var localDataSource: MessageLocalDataSource
    private lateinit var repository: MessageRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mockk()
        repository = MessageRepositoryImpl(localDataSource)
    }

    @Test
    fun `getMessages emits success with mapped messages`() = runTest {
        // Arrange
        val entities = listOf(
            MessageEntity("1", "Payment", "You received $100", "2 min ago", "TRANSACTION", false),
            MessageEntity("2", "Alert", "New login", "1 hour ago", "SECURITY", true),
        )
        every { localDataSource.observeMessages() } returns flowOf(entities)

        // Act
        repository.getMessages().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val messages = (result as Result.Success).data
            assertThat(messages).hasSize(2)
            assertThat(messages[0].type).isEqualTo(MessageType.TRANSACTION)
            assertThat(messages[1].isRead).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `getUnreadCount emits count`() = runTest {
        // Arrange
        every { localDataSource.observeUnreadCount() } returns flowOf(3)

        // Act
        repository.getUnreadCount().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(3)
            awaitComplete()
        }
    }
}
