package dev.esteki.ibank.core.data.transaction.repository

import dev.esteki.ibank.core.data.db.mapper.toDomain
import dev.esteki.ibank.core.data.transaction.datasource.TransactionLocalDataSource
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val localDataSource: TransactionLocalDataSource,
) : TransactionRepository {

    override fun getTransactions(): Flow<Result<List<Transaction>>> =
        localDataSource.observeAll().map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }

    override fun searchTransactions(query: String): Flow<Result<List<Transaction>>> =
        localDataSource.search(query).map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }
}
