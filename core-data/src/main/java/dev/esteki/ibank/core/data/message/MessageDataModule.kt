package dev.esteki.ibank.core.data.message

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.domain.message.repository.MessageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MessageDataModule {

    @Provides
    @Singleton
    fun provideMessageLocalDataSource(
        messageDao: MessageDao,
    ): MessageLocalDataSource = MessageLocalDataSource(messageDao)

    @Provides
    @Singleton
    fun provideMessageRepository(localDataSource: MessageLocalDataSource): MessageRepository =
        MessageRepositoryImpl(localDataSource)
}
