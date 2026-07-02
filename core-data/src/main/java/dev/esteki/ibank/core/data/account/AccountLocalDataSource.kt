package dev.esteki.ibank.core.data.account

import dev.esteki.ibank.core.data.account.AccountEntity
import kotlinx.coroutines.flow.Flow

class AccountLocalDataSource(
    private val accountDao: AccountDao,
) {
    fun observeAll(): Flow<List<AccountEntity>> = accountDao.observeAll()

    fun search(query: String): Flow<List<AccountEntity>> = accountDao.search(query)
}
