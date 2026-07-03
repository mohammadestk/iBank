package dev.esteki.ibank.feature.search.model

import androidx.compose.runtime.Stable
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.feature.search.model.RecentSearch
import dev.esteki.ibank.feature.search.model.SearchFilter
import dev.esteki.ibank.feature.search.model.SearchResult
import dev.esteki.ibank.feature.search.model.SuggestedPayee

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
