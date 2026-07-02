package dev.esteki.ibank.feature.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.esteki.ibank.core.domain.account.Account
import dev.esteki.ibank.core.domain.quickaction.QuickAction
import dev.esteki.ibank.core.domain.transaction.Transaction

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
