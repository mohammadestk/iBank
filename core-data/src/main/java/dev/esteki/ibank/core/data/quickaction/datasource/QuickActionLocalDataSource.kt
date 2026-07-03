package dev.esteki.ibank.core.data.quickaction.datasource

import dev.esteki.ibank.core.data.quickaction.dao.QuickActionDao
import dev.esteki.ibank.core.data.quickaction.entity.QuickActionEntity
import kotlinx.coroutines.flow.Flow

class QuickActionLocalDataSource(
    private val quickActionDao: QuickActionDao,
) {
    fun observeAll(): Flow<List<QuickActionEntity>> = quickActionDao.observeAll()
}
