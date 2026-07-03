package dev.esteki.ibank.core.data.account.datasource

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.account.dao.AccountDao
import dev.esteki.ibank.core.data.account.entity.AccountEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AccountLocalDataSourceTest {

    private lateinit var dao: AccountDao
    private lateinit var dataSource: AccountLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = AccountLocalDataSource(dao)
    }

    @Test
    fun `observeAll delegates to dao observeAll`() = runTest {
        // Arrange
        val entities = listOf(AccountEntity("1", "Main", 1000.0, "USD", "****1234", "SAVINGS"))
        every { dao.observeAll() } returns flowOf(entities)

        // Act
        dataSource.observeAll().test {
            val result = awaitItem()

            // Assert
            assertThat(result).hasSize(1)
            assertThat(result[0].id).isEqualTo("1")
            verify { dao.observeAll() }
            awaitComplete()
        }
    }

    @Test
    fun `observeAll returns empty list when dao has no data`() = runTest {
        every { dao.observeAll() } returns flowOf(emptyList())

        dataSource.observeAll().test {
            assertThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }

    @Test
    fun `search delegates query to dao search`() = runTest {
        // Arrange
        val entities = listOf(AccountEntity("1", "Main", 1000.0, "USD", "****1234", "SAVINGS"))
        every { dao.search("Main") } returns flowOf(entities)

        // Act
        dataSource.search("Main").test {
            val result = awaitItem()

            // Assert
            assertThat(result).hasSize(1)
            verify { dao.search("Main") }
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
