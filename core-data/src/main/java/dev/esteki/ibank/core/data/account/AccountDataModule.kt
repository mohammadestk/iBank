package dev.esteki.ibank.core.data.account

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.domain.account.AccountRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountDataModule {

    @Provides
    @Singleton
    fun provideAccountLocalDataSource(accountDao: AccountDao): AccountLocalDataSource =
        AccountLocalDataSource(accountDao)

    @Provides
    @Singleton
    fun provideAccountRepository(localDataSource: AccountLocalDataSource): AccountRepository =
        AccountRepositoryImpl(localDataSource)
}
