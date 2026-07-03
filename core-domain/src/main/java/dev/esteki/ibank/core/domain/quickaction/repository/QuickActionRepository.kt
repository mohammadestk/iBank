package dev.esteki.ibank.core.domain.quickaction.repository

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import kotlinx.coroutines.flow.Flow

interface QuickActionRepository {
    fun getQuickActions(): Flow<Result<List<QuickAction>>>
}
