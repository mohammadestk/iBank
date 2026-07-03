package dev.esteki.ibank.feature.search

import androidx.compose.runtime.Stable
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.transaction.model.Transaction

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
