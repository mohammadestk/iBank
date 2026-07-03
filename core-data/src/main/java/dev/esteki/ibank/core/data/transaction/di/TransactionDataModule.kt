package dev.esteki.ibank.core.data.transaction.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.data.transaction.dao.TransactionDao
import dev.esteki.ibank.core.data.transaction.datasource.TransactionLocalDataSource
import dev.esteki.ibank.core.data.transaction.repository.TransactionRepositoryImpl
import dev.esteki.ibank.core.domain.transaction.repository.TransactionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransactionDataModule {

    @Provides
    @Singleton
    fun provideTransactionLocalDataSource(transactionDao: TransactionDao): TransactionLocalDataSource =
        TransactionLocalDataSource(transactionDao)

    @Provides
    @Singleton
    fun provideTransactionRepository(localDataSource: TransactionLocalDataSource): TransactionRepository =
        TransactionRepositoryImpl(localDataSource)
}
