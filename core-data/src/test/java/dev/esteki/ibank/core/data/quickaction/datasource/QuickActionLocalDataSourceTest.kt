package dev.esteki.ibank.core.data.quickaction.datasource

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.quickaction.dao.QuickActionDao
import dev.esteki.ibank.core.data.quickaction.entity.QuickActionEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class QuickActionLocalDataSourceTest {

    private lateinit var dao: QuickActionDao
    private lateinit var dataSource: QuickActionLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = QuickActionLocalDataSource(dao)
    }

    @Test
    fun `observeAll delegates to dao observeAll`() = runTest {
        // Arrange
        val entities = listOf(
            QuickActionEntity("1", "Send", 123, "SEND"),
        )
        every { dao.observeAll() } returns flowOf(entities)

        // Act
        dataSource.observeAll().test {
            val result = awaitItem()

            // Assert
            assertThat(result).hasSize(1)
            assertThat(result[0].label).isEqualTo("Send")
            verify { dao.observeAll() }
            awaitComplete()
        }
    }

    @Test
    fun `observeAll returns empty list when no actions`() = runTest {
        every { dao.observeAll() } returns flowOf(emptyList())

        dataSource.observeAll().test {
            assertThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }
}
