package dev.esteki.ibank.core.data.account

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.account.model.AccountType
import dev.esteki.ibank.core.domain.common.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AccountRepositoryImplTest {

    private lateinit var localDataSource: AccountLocalDataSource
    private lateinit var repository: AccountRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mockk()
        repository = AccountRepositoryImpl(localDataSource)
    }

    @Test
    fun `getAccounts emits success with mapped accounts`() = runTest {
        // Arrange
        val entities = listOf(
            AccountEntity("1", "Main", 1000.0, "USD", "****1234", "SAVINGS"),
            AccountEntity("2", "Checking", 500.0, "EUR", "****5678", "CHECKING"),
        )
        every { localDataSource.observeAll() } returns flowOf(entities)

        // Act
        repository.getAccounts().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val accounts = (result as Result.Success).data
            assertThat(accounts).hasSize(2)
            assertThat(accounts[0].id).isEqualTo("1")
            assertThat(accounts[0].accountType).isEqualTo(AccountType.SAVINGS)
            assertThat(accounts[1].accountType).isEqualTo(AccountType.CHECKING)
            awaitComplete()
        }
    }

    @Test
    fun `searchAccounts delegates query to local source`() = runTest {
        // Arrange
        val entities = listOf(
            AccountEntity("1", "Main", 1000.0, "USD", "****1234", "SAVINGS"),
        )
        every { localDataSource.search("Main") } returns flowOf(entities)

        // Act
        repository.searchAccounts("Main").test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val accounts = (result as Result.Success).data
            assertThat(accounts).hasSize(1)
            assertThat(accounts[0].name).isEqualTo("Main")
            awaitComplete()
        }
    }
}
