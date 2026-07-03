package dev.esteki.ibank.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.usecase.GetAccountsUseCase
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.common.toUserMessage
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.quickaction.usecase.GetQuickActionsUseCase
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.usecase.GetTransactionsUseCase
import dev.esteki.ibank.core.domain.user.model.UserProfile
import dev.esteki.ibank.core.domain.user.usecase.GetUserProfileUseCase
import dev.esteki.ibank.feature.home.model.HomeIntent
import dev.esteki.ibank.feature.home.model.HomeResult
import dev.esteki.ibank.feature.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val initialUiState: HomeUiState,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getQuickActionsUseCase: GetQuickActionsUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        onIntent(HomeIntent.LoadData)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> loadHomeData()
            is HomeIntent.QuickActionClicked -> handleQuickAction(intent.action)
            is HomeIntent.NotificationClicked -> handleNotificationClick()
            is HomeIntent.SeeAllTransactions -> handleSeeAllTransactions()
        }
    }

    private fun loadHomeData() {
        _uiState.update { it.copy(result = HomeResult.Loading) }

        combine(
            getUserProfileUseCase(),
            getAccountsUseCase(),
            getQuickActionsUseCase(),
            getTransactionsUseCase(),
        ) { profile, accounts, quickActions, transactions ->
            val failures = listOf(profile, accounts, quickActions, transactions)
                .filterIsInstance<Result.Failure>()

            if (failures.isNotEmpty()) {
                return@combine Result.Failure(failures.first().error)
            }

            val profileData = (profile as Result.Success).data
            val accountsData = (accounts as Result.Success).data
            val quickActionsData = (quickActions as Result.Success).data
            val transactionsData = (transactions as Result.Success).data
                .sortedByDescending { it.date }
                .take(RECENT_TRANSACTION_LIMIT)

            Result.Success(
                HomeData(
                    profile = profileData,
                    totalBalance = accountsData.sumOf { it.balance },
                    accounts = accountsData,
                    quickActions = quickActionsData,
                    recentTransactions = transactionsData,
                )
            )
        }
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        _uiState.update {
                            it.copy(
                                result = HomeResult.Success(
                                    userName = data.profile.name,
                                    avatarUrl = data.profile.avatarUrl,
                                    notificationCount = data.profile.notificationCount,
                                    totalBalance = data.totalBalance,
                                ),
                                accounts = data.accounts,
                                quickActions = data.quickActions,
                                transactions = data.recentTransactions,
                            )
                        }
                    }
                    is Result.Failure -> {
                        _uiState.update {
                            it.copy(
                                result = HomeResult.Failure(
                                    error = result.error,
                                    message = result.error.toUserMessage(),
                                ),
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleQuickAction(action: QuickAction) {
        // TODO: Navigate to quick action screen
    }

    private fun handleNotificationClick() {
        // TODO: Navigate to notifications screen
    }

    private fun handleSeeAllTransactions() {
        // TODO: Navigate to all transactions screen
    }

    companion object {
        private const val RECENT_TRANSACTION_LIMIT = 5
    }
}

private data class HomeData(
    val profile: UserProfile,
    val totalBalance: Double,
    val accounts: List<Account>,
    val quickActions: List<QuickAction>,
    val recentTransactions: List<Transaction>,
)
