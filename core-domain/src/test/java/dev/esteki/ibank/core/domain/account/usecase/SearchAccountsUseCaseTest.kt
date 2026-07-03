package dev.esteki.ibank.core.domain.account.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.model.AccountType
import dev.esteki.ibank.core.domain.account.repository.AccountRepository
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchAccountsUseCaseTest {

    private lateinit var repository: AccountRepository
    private lateinit var useCase: SearchAccountsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = SearchAccountsUseCase(repository)
    }

    @Test
    fun `invoke returns matching accounts`() = runTest {
        // Arrange
        val accounts = listOf(
            Account("1", "Main", 1000.0, "USD", "****1234", AccountType.SAVINGS),
        )
        every { repository.searchAccounts("Main") } returns flowOf(Result.Success(accounts))

        // Act & Assert
        useCase("Main").test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).hasSize(1)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns empty list when no matches`() = runTest {
        every { repository.searchAccounts("xyz") } returns flowOf(Result.Success(emptyList()))

        useCase("xyz").test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEmpty()
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        every { repository.searchAccounts("Main") } returns flowOf(Result.Failure(AppError.NotFound))

        useCase("Main").test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            awaitComplete()
        }
    }
}
