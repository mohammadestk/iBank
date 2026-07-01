package dev.esteki.ibank.core.domain.settings

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.model.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): Flow<Result<Settings>> = repository.getSettings()
}
