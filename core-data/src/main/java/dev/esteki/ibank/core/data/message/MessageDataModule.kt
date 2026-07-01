package dev.esteki.ibank.core.data.message

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.domain.message.MessageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MessageDataModule {

    @Binds
    @Singleton
    abstract fun bindMessageRepository(impl: FakeMessageRepository): MessageRepository
}
