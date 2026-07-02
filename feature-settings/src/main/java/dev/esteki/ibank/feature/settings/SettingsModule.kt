package dev.esteki.ibank.feature.settings

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.esteki.ibank.core.domain.settings.SettingsSection

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
