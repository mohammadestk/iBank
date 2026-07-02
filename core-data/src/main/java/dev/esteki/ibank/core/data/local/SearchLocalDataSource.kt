package dev.esteki.ibank.core.data.local

import dev.esteki.ibank.core.data.local.dao.AccountDao
import dev.esteki.ibank.core.data.local.dao.TransactionDao
import dev.esteki.ibank.core.data.local.entity.AccountEntity
import dev.esteki.ibank.core.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

class SearchLocalDataSource(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao,
) {
    fun searchAccounts(query: String): Flow<List<AccountEntity>> = accountDao.search(query)

    fun searchTransactions(query: String): Flow<List<TransactionEntity>> = transactionDao.search(query)
}
