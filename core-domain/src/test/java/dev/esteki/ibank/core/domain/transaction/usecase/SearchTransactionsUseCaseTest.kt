package dev.esteki.ibank.core.domain.transaction.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.model.TransactionIcon
import dev.esteki.ibank.core.domain.transaction.repository.TransactionRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchTransactionsUseCaseTest {

    private lateinit var repository: TransactionRepository
    private lateinit var useCase: SearchTransactionsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = SearchTransactionsUseCase(repository)
    }

    @Test
    fun `invoke returns matching transactions`() = runTest {
        // Arrange
        val transactions = listOf(
            Transaction("1", "Spotify", "Sub", -10.99, false, TransactionIcon.MUSIC, "Today"),
        )
        every { repository.searchTransactions("Spotify") } returns flowOf(Result.Success(transactions))

        // Act & Assert
        useCase("Spotify").test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).hasSize(1)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns empty list when no matches`() = runTest {
        every { repository.searchTransactions("xyz") } returns flowOf(Result.Success(emptyList()))

        useCase("xyz").test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEmpty()
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        every { repository.searchTransactions("Spotify") } returns flowOf(Result.Failure(AppError.NotFound))

        useCase("Spotify").test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            awaitComplete()
        }
    }
}
