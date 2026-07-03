package dev.esteki.ibank.core.data.transaction.datasource

import dev.esteki.ibank.core.data.transaction.dao.TransactionDao
import dev.esteki.ibank.core.data.transaction.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

class TransactionLocalDataSource(
    private val transactionDao: TransactionDao,
) {
    fun observeAll(): Flow<List<TransactionEntity>> = transactionDao.observeAll()

    fun search(query: String): Flow<List<TransactionEntity>> = transactionDao.search(query)
}
