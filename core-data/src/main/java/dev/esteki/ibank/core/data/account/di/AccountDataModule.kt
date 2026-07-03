package dev.esteki.ibank.core.data.account.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.data.account.dao.AccountDao
import dev.esteki.ibank.core.data.account.datasource.AccountLocalDataSource
import dev.esteki.ibank.core.data.account.repository.AccountRepositoryImpl
import dev.esteki.ibank.core.domain.account.repository.AccountRepository
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
