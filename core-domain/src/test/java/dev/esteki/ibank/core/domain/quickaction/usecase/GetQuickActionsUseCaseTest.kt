package dev.esteki.ibank.core.domain.quickaction.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.quickaction.model.QuickActionType
import dev.esteki.ibank.core.domain.quickaction.repository.QuickActionRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetQuickActionsUseCaseTest {

    private lateinit var repository: QuickActionRepository
    private lateinit var useCase: GetQuickActionsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetQuickActionsUseCase(repository)
    }

    @Test
    fun `invoke returns quick actions from repository`() = runTest {
        // Arrange
        val actions = listOf(
            QuickAction("1", "Send", 123, QuickActionType.SEND),
        )
        every { repository.getQuickActions() } returns flowOf(Result.Success(actions))

        // Act & Assert
        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(actions)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        every { repository.getQuickActions() } returns flowOf(Result.Failure(AppError.Network))

        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.Network)
            awaitComplete()
        }
    }
}
