package dev.esteki.ibank.core.data.quickaction

import dev.esteki.ibank.core.data.quickaction.QuickActionEntity
import kotlinx.coroutines.flow.Flow

class QuickActionLocalDataSource(
    private val quickActionDao: QuickActionDao,
) {
    fun observeAll(): Flow<List<QuickActionEntity>> = quickActionDao.observeAll()
}
