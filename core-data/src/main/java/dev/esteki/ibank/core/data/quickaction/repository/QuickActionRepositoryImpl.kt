package dev.esteki.ibank.core.data.quickaction.repository

import dev.esteki.ibank.core.data.db.mapper.toDomain
import dev.esteki.ibank.core.data.quickaction.datasource.QuickActionLocalDataSource
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.quickaction.repository.QuickActionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuickActionRepositoryImpl(
    private val localDataSource: QuickActionLocalDataSource,
) : QuickActionRepository {

    override fun getQuickActions(): Flow<Result<List<QuickAction>>> =
        localDataSource.observeAll().map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }
}
