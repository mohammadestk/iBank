package dev.esteki.ibank.core.data.transaction.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.transaction.datasource.TransactionLocalDataSource
import dev.esteki.ibank.core.data.transaction.entity.TransactionEntity
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.transaction.model.TransactionIcon
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TransactionRepositoryImplTest {

    private lateinit var localDataSource: TransactionLocalDataSource
    private lateinit var repository: TransactionRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mockk()
        repository = TransactionRepositoryImpl(localDataSource)
    }

    @Test
    fun `getTransactions emits success with mapped transactions`() = runTest {
        // Arrange
        val entities = listOf(
            TransactionEntity("1", "Spotify", "Sub", -10.99, false, "MUSIC", "Today"),
            TransactionEntity("2", "Salary", "Deposit", 4200.0, true, "SALARY", "Yesterday"),
        )
        every { localDataSource.observeAll() } returns flowOf(entities)

        // Act
        repository.getTransactions().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val transactions = (result as Result.Success).data
            assertThat(transactions).hasSize(2)
            assertThat(transactions[0].icon).isEqualTo(TransactionIcon.MUSIC)
            assertThat(transactions[1].isPositive).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `searchTransactions delegates query to local source`() = runTest {
        // Arrange
        val entities = listOf(
            TransactionEntity("1", "Spotify", "Sub", -10.99, false, "MUSIC", "Today"),
        )
        every { localDataSource.search("Spotify") } returns flowOf(entities)

        // Act
        repository.searchTransactions("Spotify").test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val transactions = (result as Result.Success).data
            assertThat(transactions).hasSize(1)
            assertThat(transactions[0].name).isEqualTo("Spotify")
            awaitComplete()
        }
    }
}
