package dev.esteki.ibank.core.domain.transaction

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(): Flow<Result<List<Transaction>>>
    fun searchTransactions(query: String): Flow<Result<List<Transaction>>>
}
