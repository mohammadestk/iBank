package dev.esteki.ibank.core.domain.quickaction

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.QuickAction
import kotlinx.coroutines.flow.Flow

interface QuickActionRepository {
    fun getQuickActions(): Flow<Result<List<QuickAction>>>
}
