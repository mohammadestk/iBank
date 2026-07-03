package dev.esteki.ibank.core.domain.account.repository

import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.common.Result
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(): Flow<Result<List<Account>>>
    fun searchAccounts(query: String): Flow<Result<List<Account>>>
}
