package dev.esteki.ibank.feature.search

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.common.toUserMessage
import dev.esteki.ibank.core.domain.account.Account
import dev.esteki.ibank.core.domain.transaction.Transaction
import dev.esteki.ibank.core.domain.search.SearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class SearchFilter(val label: String) {
    ALL("All"),
    PAYEES("Payees"),
    TRANSACTIONS("Transactions"),
    HELP("Help"),
}

data class RecentSearch(
    val id: String,
    val query: String,
)

data class SuggestedPayee(
    val id: String,
    val name: String,
    val initials: String,
)

@Stable
data class SearchUiState(
    val result: SearchResult,
    val query: String,
    val selectedFilter: SearchFilter,
    val accounts: List<Account>,
    val transactions: List<Transaction>,
    val recentSearches: List<RecentSearch>,
    val suggestedPayees: List<SuggestedPayee>,
)

sealed interface SearchResult {
    data object Idle : SearchResult
    data object Loading : SearchResult
    data object Empty : SearchResult
    data class Success(val accounts: List<Account>, val transactions: List<Transaction>) : SearchResult
    data class Failure(val error: AppError, val message: String) : SearchResult
}

sealed interface SearchIntent {
    data class QueryChanged(val query: String) : SearchIntent
    data class FilterSelected(val filter: SearchFilter) : SearchIntent
    data class RecentSearchClicked(val search: RecentSearch) : SearchIntent
    data class PayeeClicked(val payee: SuggestedPayee) : SearchIntent
    data object ClearSearch : SearchIntent
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val initialUiState: SearchUiState,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.QueryChanged -> search(intent.query)
            is SearchIntent.FilterSelected -> selectFilter(intent.filter)
            is SearchIntent.RecentSearchClicked -> onRecentSearchClick(intent.search)
            is SearchIntent.PayeeClicked -> onPayeeClick(intent.payee)
            is SearchIntent.ClearSearch -> clearSearch()
        }
    }

    private fun search(query: String) {
        _uiState.update { it.copy(query = query) }

        if (query.isBlank()) {
            _uiState.update {
                it.copy(
                    result = SearchResult.Idle,
                    accounts = emptyList(),
                    transactions = emptyList(),
                )
            }
            return
        }

        _uiState.update { it.copy(result = SearchResult.Loading) }

        searchUseCase(query)
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        if (data.accounts.isEmpty() && data.transactions.isEmpty()) {
                            _uiState.update {
                                it.copy(
                                    result = SearchResult.Empty,
                                    accounts = emptyList(),
                                    transactions = emptyList(),
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    result = SearchResult.Success(
                                        accounts = data.accounts,
                                        transactions = data.transactions,
                                    ),
                                    accounts = data.accounts,
                                    transactions = data.transactions,
                                )
                            }
                        }
                    }
                    is Result.Failure -> {
                        _uiState.update {
                            it.copy(
                                result = SearchResult.Failure(
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

    private fun selectFilter(filter: SearchFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    private fun onRecentSearchClick(search: RecentSearch) {
        search(search.query)
    }

    private fun onPayeeClick(payee: SuggestedPayee) {
        search(payee.name)
    }

    private fun clearSearch() {
        _uiState.update {
            it.copy(
                query = "",
                result = SearchResult.Idle,
                accounts = emptyList(),
                transactions = emptyList(),
            )
        }
    }
}
