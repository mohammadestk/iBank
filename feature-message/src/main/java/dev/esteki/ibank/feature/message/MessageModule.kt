package dev.esteki.ibank.feature.message

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

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
