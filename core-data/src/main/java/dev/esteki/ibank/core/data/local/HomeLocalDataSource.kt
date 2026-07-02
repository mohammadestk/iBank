package dev.esteki.ibank.core.data.local

import dev.esteki.ibank.core.data.local.dao.AccountDao
import dev.esteki.ibank.core.data.local.dao.QuickActionDao
import dev.esteki.ibank.core.data.local.dao.TransactionDao
import dev.esteki.ibank.core.data.local.dao.UserProfileDao
import dev.esteki.ibank.core.data.local.entity.AccountEntity
import dev.esteki.ibank.core.data.local.entity.QuickActionEntity
import dev.esteki.ibank.core.data.local.entity.TransactionEntity
import dev.esteki.ibank.core.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class HomeLocalDataSource(
    private val userProfileDao: UserProfileDao,
    private val accountDao: AccountDao,
    private val quickActionDao: QuickActionDao,
    private val transactionDao: TransactionDao,
) {
    fun observeProfile(): Flow<UserProfileEntity?> = userProfileDao.observeProfile()

    fun observeAccounts(): Flow<List<AccountEntity>> = accountDao.observeAll()

    fun observeQuickActions(): Flow<List<QuickActionEntity>> = quickActionDao.observeAll()

    fun observeTransactions(): Flow<List<TransactionEntity>> = transactionDao.observeAll()
}
