package dev.esteki.ibank.feature.search

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @Provides
    @ViewModelScoped
    fun provideSearchUiState(): SearchUiState = SearchUiState(
        result = SearchResult.Idle,
        query = "",
        selectedFilter = SearchFilter.ALL,
        accounts = emptyList(),
        transactions = emptyList(),
        recentSearches = listOf(
            RecentSearch("1", "Whole Foods"),
            RecentSearch("2", "Send to Maya"),
            RecentSearch("3", "Statement October"),
        ),
        suggestedPayees = listOf(
            SuggestedPayee("1", "Maya", "MR"),
            SuggestedPayee("2", "Daniel", "DK"),
            SuggestedPayee("3", "Sara", "SL"),
        ),
    )
}
