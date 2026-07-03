package dev.esteki.ibank.feature.search

sealed interface SearchIntent {
    data class QueryChanged(val query: String) : SearchIntent
    data class FilterSelected(val filter: SearchFilter) : SearchIntent
    data class RecentSearchClicked(val search: RecentSearch) : SearchIntent
    data class PayeeClicked(val payee: SuggestedPayee) : SearchIntent
    data object ClearSearch : SearchIntent
}
