package dev.esteki.ibank.feature.search.viewmodel

import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.model.AccountType
import dev.esteki.ibank.core.domain.account.usecase.SearchAccountsUseCase
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.model.TransactionIcon
import dev.esteki.ibank.core.domain.transaction.usecase.SearchTransactionsUseCase
import dev.esteki.ibank.feature.search.model.RecentSearch
import dev.esteki.ibank.feature.search.model.SearchFilter
import dev.esteki.ibank.feature.search.model.SearchIntent
import dev.esteki.ibank.feature.search.model.SearchResult
import dev.esteki.ibank.feature.search.model.SearchUiState
import dev.esteki.ibank.feature.search.model.SuggestedPayee
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var searchAccountsUseCase: SearchAccountsUseCase
    private lateinit var searchTransactionsUseCase: SearchTransactionsUseCase

    private val accounts = listOf(
        Account("1", "Main Account", 1000.0, "USD", "****1234", AccountType.SAVINGS),
    )
    private val transactions = listOf(
        Transaction("1", "Spotify", "Sub", -10.99, false, TransactionIcon.MUSIC, "Today"),
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchAccountsUseCase = mockk()
        searchTransactionsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = SearchViewModel(
        initialUiState = SearchUiState(
            result = SearchResult.Idle,
            query = "",
            selectedFilter = SearchFilter.ALL,
            accounts = emptyList(),
            transactions = emptyList(),
            recentSearches = emptyList(),
            suggestedPayees = emptyList(),
        ),
        searchAccountsUseCase = searchAccountsUseCase,
        searchTransactionsUseCase = searchTransactionsUseCase,
    )

    @Test
    fun `blank query results in Idle state`() = runTest {
        val viewModel = createViewModel()
        viewModel.onIntent(SearchIntent.QueryChanged(""))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(SearchResult.Idle::class.java)
        assertThat(state.query).isEmpty()
    }

    @Test
    fun `search with results shows Success state`() = runTest {
        every { searchAccountsUseCase("Main") } returns flowOf(Result.Success(accounts))
        every { searchTransactionsUseCase("Main") } returns flowOf(Result.Success(transactions))

        val viewModel = createViewModel()
        viewModel.onIntent(SearchIntent.QueryChanged("Main"))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(SearchResult.Success::class.java)
        val result = state.result as SearchResult.Success
        assertThat(result.accounts).hasSize(1)
        assertThat(result.transactions).hasSize(1)
    }

    @Test
    fun `search with no results shows Empty state`() = runTest {
        every { searchAccountsUseCase("xyz") } returns flowOf(Result.Success(emptyList()))
        every { searchTransactionsUseCase("xyz") } returns flowOf(Result.Success(emptyList()))

        val viewModel = createViewModel()
        viewModel.onIntent(SearchIntent.QueryChanged("xyz"))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(SearchResult.Empty::class.java)
    }

    @Test
    fun `search with failure shows Failure state`() = runTest {
        every { searchAccountsUseCase("Main") } returns flowOf(Result.Failure(AppError.Network))
        every { searchTransactionsUseCase("Main") } returns flowOf(Result.Success(emptyList()))

        val viewModel = createViewModel()
        viewModel.onIntent(SearchIntent.QueryChanged("Main"))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(SearchResult.Failure::class.java)
        assertThat((state.result as SearchResult.Failure).error).isEqualTo(AppError.Network)
    }

    @Test
    fun `selectFilter updates selected filter`() = runTest {
        val viewModel = createViewModel()
        viewModel.onIntent(SearchIntent.FilterSelected(SearchFilter.TRANSACTIONS))

        val state = viewModel.uiState.value
        assertThat(state.selectedFilter).isEqualTo(SearchFilter.TRANSACTIONS)
    }

    @Test
    fun `clearSearch resets state to Idle`() = runTest {
        every { searchAccountsUseCase("Main") } returns flowOf(Result.Success(accounts))
        every { searchTransactionsUseCase("Main") } returns flowOf(Result.Success(transactions))

        val viewModel = createViewModel()
        viewModel.onIntent(SearchIntent.QueryChanged("Main"))
        advanceUntilIdle()

        viewModel.onIntent(SearchIntent.ClearSearch)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(SearchResult.Idle::class.java)
        assertThat(state.query).isEmpty()
        assertThat(state.accounts).isEmpty()
        assertThat(state.transactions).isEmpty()
    }

    @Test
    fun `recent search click triggers search with that query`() = runTest {
        every { searchAccountsUseCase("Whole Foods") } returns flowOf(Result.Success(emptyList()))
        every { searchTransactionsUseCase("Whole Foods") } returns flowOf(Result.Success(emptyList()))

        val viewModel = createViewModel()
        viewModel.onIntent(SearchIntent.RecentSearchClicked(RecentSearch("1", "Whole Foods")))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.query).isEqualTo("Whole Foods")
    }

    @Test
    fun `payee click triggers search with payee name`() = runTest {
        every { searchAccountsUseCase("Maya") } returns flowOf(Result.Success(emptyList()))
        every { searchTransactionsUseCase("Maya") } returns flowOf(Result.Success(emptyList()))

        val viewModel = createViewModel()
        viewModel.onIntent(SearchIntent.PayeeClicked(SuggestedPayee("1", "Maya", "MR")))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.query).isEqualTo("Maya")
    }
}
