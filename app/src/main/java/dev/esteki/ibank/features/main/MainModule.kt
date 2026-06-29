package dev.esteki.ibank.features.main

import androidx.compose.runtime.mutableStateListOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.esteki.ibank.BottomDestinations
import dev.esteki.ibank.BottomRoute

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    @ViewModelScoped
    fun provideMainUiState(): MainUiState = MainUiState(
        selectedRoute = BottomDestinations.first(),
        backStack = mutableStateListOf<BottomRoute>().apply {
            add(BottomDestinations.first())
        },
    )
}
