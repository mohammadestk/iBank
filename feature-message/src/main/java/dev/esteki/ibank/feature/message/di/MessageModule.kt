package dev.esteki.ibank.feature.message.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.esteki.ibank.feature.message.model.MessageFilter
import dev.esteki.ibank.feature.message.model.MessageResult
import dev.esteki.ibank.feature.message.model.MessageUiState

@Module
@InstallIn(ViewModelComponent::class)
object MessageModule {

    @Provides
    @ViewModelScoped
    fun provideMessageUiState(): MessageUiState = MessageUiState(
        result = MessageResult.Idle,
        messages = emptyList(),
        filteredMessages = emptyList(),
        unreadCount = 0,
        selectedFilter = MessageFilter.INBOX,
    )
}
