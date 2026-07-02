package dev.esteki.ibank.core.data.home

import dev.esteki.ibank.core.data.local.HomeLocalDataSource
import dev.esteki.ibank.core.data.local.entity.toDomain
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.home.HomeRepository
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.QuickAction
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val localDataSource: HomeLocalDataSource,
) : HomeRepository {

    override fun getUserProfile(): Flow<Result<UserProfile>> =
        localDataSource.observeProfile().map { entity ->
            if (entity != null) {
                Result.Success(entity.toDomain())
            } else {
                Result.Failure(AppError.NotFound)
            }
        }

    override fun getAccounts(): Flow<Result<List<Account>>> =
        localDataSource.observeAccounts().map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }

    override fun getQuickActions(): Flow<Result<List<QuickAction>>> =
        localDataSource.observeQuickActions().map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }

    override fun getTransactions(): Flow<Result<List<Transaction>>> =
        localDataSource.observeTransactions().map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }

    override fun getNotificationCount(): Flow<Result<Int>> =
        localDataSource.observeProfile().map { entity ->
            if (entity != null) {
                Result.Success(entity.notificationCount)
            } else {
                Result.Success(0)
            }
        }
}
