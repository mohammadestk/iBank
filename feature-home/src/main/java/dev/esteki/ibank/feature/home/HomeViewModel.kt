package dev.esteki.ibank.feature.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteki.ibank.core.domain.home.GetHomeDataUseCase
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.QuickAction
import dev.esteki.ibank.core.domain.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class HomeUiState(
    val result: HomeResult,
    val accounts: List<Account>,
    val quickActions: List<QuickAction>,
    val transactions: List<Transaction>,
)

sealed interface HomeResult {
    data object Idle : HomeResult
    data object Loading : HomeResult
    data class Success(
        val userName: String,
        val avatarUrl: String,
        val notificationCount: Int,
    ) : HomeResult
    data class Failure(val error: String) : HomeResult
}

sealed interface HomeIntent {
    data object LoadData : HomeIntent
    data class QuickActionClicked(val action: QuickAction) : HomeIntent
    data object NotificationClicked : HomeIntent
    data object SeeAllTransactions : HomeIntent
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val initialUiState: HomeUiState,
    private val getHomeDataUseCase: GetHomeDataUseCase,
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
        viewModelScope.launch {
            _uiState.update { it.copy(result = HomeResult.Loading) }
            val result = runCatching { getHomeDataUseCase() }
            result.onSuccess { data ->
                _uiState.update {
                    it.copy(
                        result = HomeResult.Success(
                            userName = data.profile.name,
                            avatarUrl = data.profile.avatarUrl,
                            notificationCount = data.profile.notificationCount,
                        ),
                        accounts = data.accounts,
                        quickActions = data.quickActions,
                        transactions = data.transactions,
                    )
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        result = HomeResult.Failure(
                            error = e.message ?: "An unexpected error occurred",
                        ),
                    )
                }
            }
        }
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
}
