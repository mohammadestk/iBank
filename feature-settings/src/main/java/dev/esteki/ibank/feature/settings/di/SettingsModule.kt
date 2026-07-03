package dev.esteki.ibank.feature.settings.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.esteki.ibank.feature.settings.model.SettingsResult
import dev.esteki.ibank.feature.settings.model.SettingsUiState

@Module
@InstallIn(ViewModelComponent::class)
object SettingsModule {

    @Provides
    @ViewModelScoped
    fun provideSettingsUiState(): SettingsUiState = SettingsUiState(
        result = SettingsResult.Idle,
        profile = null,
        sections = emptyList(),
    )
}
