package dev.esteki.ibank.core.data.search

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.data.local.SearchLocalDataSource
import dev.esteki.ibank.core.data.local.dao.AccountDao
import dev.esteki.ibank.core.data.local.dao.TransactionDao
import dev.esteki.ibank.core.domain.search.SearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchDataModule {

    @Provides
    @Singleton
    fun provideSearchLocalDataSource(
        accountDao: AccountDao,
        transactionDao: TransactionDao,
    ): SearchLocalDataSource = SearchLocalDataSource(accountDao, transactionDao)

    @Provides
    @Singleton
    fun provideSearchRepository(localDataSource: SearchLocalDataSource): SearchRepository =
        SearchRepositoryImpl(localDataSource)
}
