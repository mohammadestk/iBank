package dev.esteki.ibank.core.data.message.datasource

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.message.dao.MessageDao
import dev.esteki.ibank.core.data.message.entity.MessageEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MessageLocalDataSourceTest {

    private lateinit var dao: MessageDao
    private lateinit var dataSource: MessageLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = MessageLocalDataSource(dao)
    }

    @Test
    fun `observeMessages delegates to dao observeAll`() = runTest {
        // Arrange
        val entities = listOf(
            MessageEntity("1", "Payment", "Desc", "2 min ago", "TRANSACTION", false),
        )
        every { dao.observeAll() } returns flowOf(entities)

        // Act
        dataSource.observeMessages().test {
            val result = awaitItem()

            // Assert
            assertThat(result).hasSize(1)
            assertThat(result[0].title).isEqualTo("Payment")
            verify { dao.observeAll() }
            awaitComplete()
        }
    }

    @Test
    fun `observeMessages returns empty list when no messages`() = runTest {
        every { dao.observeAll() } returns flowOf(emptyList())

        dataSource.observeMessages().test {
            assertThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }

    @Test
    fun `observeUnreadCount delegates to dao observeUnreadCount`() = runTest {
        every { dao.observeUnreadCount() } returns flowOf(5)

        dataSource.observeUnreadCount().test {
            assertThat(awaitItem()).isEqualTo(5)
            verify { dao.observeUnreadCount() }
            awaitComplete()
        }
    }

    @Test
    fun `observeUnreadCount returns 0 when no unread messages`() = runTest {
        every { dao.observeUnreadCount() } returns flowOf(0)

        dataSource.observeUnreadCount().test {
            assertThat(awaitItem()).isEqualTo(0)
            awaitComplete()
        }
    }
}
