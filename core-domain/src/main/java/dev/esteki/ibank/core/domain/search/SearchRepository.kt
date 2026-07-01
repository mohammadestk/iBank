package dev.esteki.ibank.core.domain.search

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchAccounts(query: String): Flow<Result<List<Account>>>
    fun searchTransactions(query: String): Flow<Result<List<Transaction>>>
}
