package dev.esteki.ibank.core.data.account.repository

import dev.esteki.ibank.core.data.account.datasource.AccountLocalDataSource
import dev.esteki.ibank.core.data.db.mapper.toDomain
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.repository.AccountRepository
import dev.esteki.ibank.core.domain.common.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRepositoryImpl(
    private val localDataSource: AccountLocalDataSource,
) : AccountRepository {

    override fun getAccounts(): Flow<Result<List<Account>>> =
        localDataSource.observeAll().map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }

    override fun searchAccounts(query: String): Flow<Result<List<Account>>> =
        localDataSource.search(query).map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }
}
