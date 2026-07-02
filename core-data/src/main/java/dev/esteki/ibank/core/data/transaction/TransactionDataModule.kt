package dev.esteki.ibank.core.data.transaction

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.domain.transaction.TransactionRepository
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
