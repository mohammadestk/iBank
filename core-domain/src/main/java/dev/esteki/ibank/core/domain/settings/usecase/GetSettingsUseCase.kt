package dev.esteki.ibank.core.domain.settings.usecase

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.settings.model.Settings
import dev.esteki.ibank.core.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): Flow<Result<Settings>> = repository.getSettings()
}
