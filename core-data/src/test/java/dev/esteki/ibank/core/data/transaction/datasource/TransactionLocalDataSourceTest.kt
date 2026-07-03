package dev.esteki.ibank.core.data.transaction.datasource

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.transaction.dao.TransactionDao
import dev.esteki.ibank.core.data.transaction.entity.TransactionEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TransactionLocalDataSourceTest {

    private lateinit var dao: TransactionDao
    private lateinit var dataSource: TransactionLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = TransactionLocalDataSource(dao)
    }

    @Test
    fun `observeAll delegates to dao observeAll`() = runTest {
        // Arrange
        val entities = listOf(
            TransactionEntity("1", "Spotify", "Sub", -10.99, false, "MUSIC", "Today"),
        )
        every { dao.observeAll() } returns flowOf(entities)

        // Act
        dataSource.observeAll().test {
            val result = awaitItem()

            // Assert
            assertThat(result).hasSize(1)
            assertThat(result[0].name).isEqualTo("Spotify")
            verify { dao.observeAll() }
            awaitComplete()
        }
    }

    @Test
    fun `observeAll returns empty list when no transactions`() = runTest {
        every { dao.observeAll() } returns flowOf(emptyList())

        dataSource.observeAll().test {
            assertThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }

    @Test
    fun `search delegates query to dao search`() = runTest {
        // Arrange
        val entities = listOf(
            TransactionEntity("1", "Spotify", "Sub", -10.99, false, "MUSIC", "Today"),
        )
        every { dao.search("Spotify") } returns flowOf(entities)

        // Act
        dataSource.search("Spotify").test {
            val result = awaitItem()

            // Assert
            assertThat(result).hasSize(1)
            verify { dao.search("Spotify") }
            awaitComplete()
        }
    }

    @Test
    fun `search returns empty when no matches`() = runTest {
        every { dao.search("xyz") } returns flowOf(emptyList())

        dataSource.search("xyz").test {
            assertThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }
}
