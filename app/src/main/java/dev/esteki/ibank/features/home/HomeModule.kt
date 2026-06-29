package dev.esteki.ibank.features.home

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
        avatar = "https://picsum.photos/200",
        title = "Hi, Mohammad Esteki!",
        notificationCount = 5,
    )
}
