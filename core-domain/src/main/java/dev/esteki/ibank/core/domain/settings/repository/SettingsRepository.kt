package dev.esteki.ibank.core.domain.settings.repository

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.settings.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Result<Settings>>
}
