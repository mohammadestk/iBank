package dev.esteki.ibank.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.usecase.SearchAccountsUseCase
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.common.toUserMessage
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.usecase.SearchTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val initialUiState: SearchUiState,
    private val searchAccountsUseCase: SearchAccountsUseCase,
    private val searchTransactionsUseCase: SearchTransactionsUseCase,
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

        combine(
            searchAccountsUseCase(query),
            searchTransactionsUseCase(query),
        ) { accounts, transactions ->
            val failures = listOf(accounts, transactions)
                .filterIsInstance<Result.Failure>()

            if (failures.isNotEmpty()) {
                return@combine Result.Failure(failures.first().error)
            }

            val accountsData = (accounts as Result.Success).data
            val transactionsData = (transactions as Result.Success).data

            Result.Success(
                SearchData(
                    accounts = accountsData,
                    transactions = transactionsData,
                )
            )
        }
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

private data class SearchData(
    val accounts: List<Account>,
    val transactions: List<Transaction>,
)
