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

class GetTransactionsUseCaseTest {

    private lateinit var repository: TransactionRepository
    private lateinit var useCase: GetTransactionsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTransactionsUseCase(repository)
    }

    @Test
    fun `invoke returns transactions from repository`() = runTest {
        // Arrange
        val transactions = listOf(
            Transaction("1", "Spotify", "Sub", -10.99, false, TransactionIcon.MUSIC, "Today"),
        )
        every { repository.getTransactions() } returns flowOf(Result.Success(transactions))

        // Act & Assert
        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(transactions)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        every { repository.getTransactions() } returns flowOf(Result.Failure(AppError.Timeout))

        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.Timeout)
            awaitComplete()
        }
    }
}
