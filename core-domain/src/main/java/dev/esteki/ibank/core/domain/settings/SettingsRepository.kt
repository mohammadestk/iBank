package dev.esteki.ibank.core.domain.settings

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.settings.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Result<Settings>>
}
