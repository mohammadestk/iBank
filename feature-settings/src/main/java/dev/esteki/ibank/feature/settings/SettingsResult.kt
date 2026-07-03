package dev.esteki.ibank.feature.settings

import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.settings.model.Settings

sealed interface SettingsResult {
    data object Idle : SettingsResult
    data object Loading : SettingsResult
    data class Success(val settings: Settings) : SettingsResult
    data class Failure(val error: AppError, val message: String) : SettingsResult
}
