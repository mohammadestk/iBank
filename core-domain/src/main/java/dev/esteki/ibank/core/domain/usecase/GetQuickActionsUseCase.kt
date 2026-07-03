package dev.esteki.ibank.core.domain.usecase

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.quickaction.repository.QuickActionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuickActionsUseCase @Inject constructor(
    private val repository: QuickActionRepository,
) {
    operator fun invoke(): Flow<Result<List<QuickAction>>> = repository.getQuickActions()
}
