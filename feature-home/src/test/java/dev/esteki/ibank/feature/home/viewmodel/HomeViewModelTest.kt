package dev.esteki.ibank.feature.home.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.model.AccountType
import dev.esteki.ibank.core.domain.account.usecase.GetAccountsUseCase
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.quickaction.model.QuickActionType
import dev.esteki.ibank.core.domain.quickaction.usecase.GetQuickActionsUseCase
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.model.TransactionIcon
import dev.esteki.ibank.core.domain.transaction.usecase.GetTransactionsUseCase
import dev.esteki.ibank.core.domain.user.model.UserProfile
import dev.esteki.ibank.core.domain.user.usecase.GetUserProfileUseCase
import dev.esteki.ibank.core.presentation.R
import dev.esteki.ibank.feature.home.model.HomeResult
import dev.esteki.ibank.feature.home.model.HomeUiState
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
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getUserProfileUseCase: GetUserProfileUseCase
    private lateinit var getAccountsUseCase: GetAccountsUseCase
    private lateinit var getQuickActionsUseCase: GetQuickActionsUseCase
    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    private val profile = UserProfile("1", "John", "url", 5)
    private val accounts = listOf(
        Account("1", "Main", 1000.0, "USD", "****1234", AccountType.SAVINGS),
        Account("2", "Checking", 500.0, "EUR", "****5678", AccountType.CHECKING),
    )
    private val quickActions = listOf(
        QuickAction("1", "Send", R.drawable.ic_ring, QuickActionType.SEND),
    )
    private val transactions = listOf(
        Transaction("1", "Spotify", "Sub", -10.99, false, TransactionIcon.MUSIC, "Today"),
        Transaction("2", "Salary", "Deposit", 4200.0, true, TransactionIcon.SALARY, "Yesterday"),
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getUserProfileUseCase = mockk()
        getAccountsUseCase = mockk()
        getQuickActionsUseCase = mockk()
        getTransactionsUseCase = mockk()

        every { getUserProfileUseCase() } returns flowOf(Result.Success(profile))
        every { getAccountsUseCase() } returns flowOf(Result.Success(accounts))
        every { getQuickActionsUseCase() } returns flowOf(Result.Success(quickActions))
        every { getTransactionsUseCase() } returns flowOf(Result.Success(transactions))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = HomeViewModel(
        initialUiState = HomeUiState(
            result = HomeResult.Idle,
            accounts = emptyList(),
            quickActions = emptyList(),
            transactions = emptyList(),
        ),
        getUserProfileUseCase = getUserProfileUseCase,
        getAccountsUseCase = getAccountsUseCase,
        getQuickActionsUseCase = getQuickActionsUseCase,
        getTransactionsUseCase = getTransactionsUseCase,
    )

    @Test
    fun `loadHomeData succeeds with all data`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(HomeResult.Success::class.java)
        val result = state.result as HomeResult.Success
        assertThat(result.userName).isEqualTo("John")
        assertThat(result.totalBalance).isEqualTo(1500.0)
        assertThat(state.accounts).hasSize(2)
        assertThat(state.quickActions).hasSize(1)
        assertThat(state.transactions).hasSize(2)
    }

    @Test
    fun `loadHomeData sorts transactions by date descending`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        // sortedByDescending on string dates: "Y" > "T" so Yesterday comes first
        assertThat(state.transactions[0].date).isEqualTo("Yesterday")
        assertThat(state.transactions[1].date).isEqualTo("Today")
    }

    @Test
    fun `loadHomeData shows failure when profile use case fails`() = runTest {
        every { getUserProfileUseCase() } returns flowOf(Result.Failure(AppError.Network))

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(HomeResult.Failure::class.java)
        assertThat((state.result as HomeResult.Failure).error).isEqualTo(AppError.Network)
    }

    @Test
    fun `loadHomeData shows failure when accounts use case fails`() = runTest {
        every { getAccountsUseCase() } returns flowOf(Result.Failure(AppError.Timeout))

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(HomeResult.Failure::class.java)
        assertThat((state.result as HomeResult.Failure).error).isEqualTo(AppError.Timeout)
    }

    @Test
    fun `loadHomeData calculates total balance from accounts`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        val success = state.result as HomeResult.Success
        assertThat(success.totalBalance).isEqualTo(1500.0)
    }

    @Test
    fun `loadHomeData limits transactions to 5`() = runTest {
        val manyTransactions = (1..10).map {
            Transaction(it.toString(), "T$it", "Desc", it.toDouble(), true, TransactionIcon.SALARY, "Day$it")
        }
        every { getTransactionsUseCase() } returns flowOf(Result.Success(manyTransactions))

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.transactions).hasSize(5)
    }
}
