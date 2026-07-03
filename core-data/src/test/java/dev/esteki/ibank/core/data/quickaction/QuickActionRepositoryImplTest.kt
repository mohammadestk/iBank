package dev.esteki.ibank.core.data.quickaction

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.model.QuickActionType
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class QuickActionRepositoryImplTest {

    private lateinit var localDataSource: QuickActionLocalDataSource
    private lateinit var repository: QuickActionRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mockk()
        repository = QuickActionRepositoryImpl(localDataSource)
    }

    @Test
    fun `getQuickActions emits success with mapped actions`() = runTest {
        // Arrange
        val entities = listOf(
            QuickActionEntity("1", "Send", 123, "SEND"),
            QuickActionEntity("2", "Request", 456, "RECEIVE"),
        )
        every { localDataSource.observeAll() } returns flowOf(entities)

        // Act
        repository.getQuickActions().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val actions = (result as Result.Success).data
            assertThat(actions).hasSize(2)
            assertThat(actions[0].type).isEqualTo(QuickActionType.SEND)
            assertThat(actions[1].type).isEqualTo(QuickActionType.RECEIVE)
            awaitComplete()
        }
    }
}
