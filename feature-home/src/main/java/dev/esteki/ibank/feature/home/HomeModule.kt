package dev.esteki.ibank.feature.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    @ViewModelScoped
    fun provideHomeUiState(): HomeUiState = HomeUiState(
        avatar = "",
        title = "",
        notificationCount = 0,
    )
}
