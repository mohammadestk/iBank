package dev.esteki.ibank.core.data.search

import dev.esteki.ibank.core.data.local.SearchLocalDataSource
import dev.esteki.ibank.core.data.local.entity.toDomain
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(
    private val localDataSource: SearchLocalDataSource,
) : SearchRepository {

    override fun searchAccounts(query: String): Flow<Result<List<Account>>> =
        localDataSource.searchAccounts(query).map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }

    override fun searchTransactions(query: String): Flow<Result<List<Transaction>>> =
        localDataSource.searchTransactions(query).map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }
}
