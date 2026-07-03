package dev.esteki.ibank.feature.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.feature.home.model.HomeResult
import dev.esteki.ibank.feature.home.model.HomeUiState

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    @ViewModelScoped
    fun provideHomeUiState(): HomeUiState = HomeUiState(
        result = HomeResult.Idle,
        accounts = emptyList(),
        quickActions = emptyList(),
        transactions = emptyList(),
    )
}
