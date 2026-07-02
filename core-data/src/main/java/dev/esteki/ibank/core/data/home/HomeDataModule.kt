package dev.esteki.ibank.core.data.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.data.local.HomeLocalDataSource
import dev.esteki.ibank.core.data.local.dao.AccountDao
import dev.esteki.ibank.core.data.local.dao.QuickActionDao
import dev.esteki.ibank.core.data.local.dao.TransactionDao
import dev.esteki.ibank.core.data.local.dao.UserProfileDao
import dev.esteki.ibank.core.domain.home.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeDataModule {

    @Provides
    @Singleton
    fun provideHomeLocalDataSource(
        userProfileDao: UserProfileDao,
        accountDao: AccountDao,
        quickActionDao: QuickActionDao,
        transactionDao: TransactionDao,
    ): HomeLocalDataSource = HomeLocalDataSource(userProfileDao, accountDao, quickActionDao, transactionDao)

    @Provides
    @Singleton
    fun provideHomeRepository(localDataSource: HomeLocalDataSource): HomeRepository =
        HomeRepositoryImpl(localDataSource)
}
