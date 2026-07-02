package dev.esteki.ibank.core.domain.account

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.account.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(): Flow<Result<List<Account>>>
    fun searchAccounts(query: String): Flow<Result<List<Account>>>
}
