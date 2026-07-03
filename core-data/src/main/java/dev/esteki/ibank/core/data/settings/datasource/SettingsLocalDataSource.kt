package dev.esteki.ibank.core.data.settings.datasource

import dev.esteki.ibank.core.data.settings.dao.SettingsDao
import dev.esteki.ibank.core.data.settings.entity.SettingsEntity
import dev.esteki.ibank.core.data.user.dao.UserProfileDao
import dev.esteki.ibank.core.data.user.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class SettingsLocalDataSource(
    private val settingsDao: SettingsDao,
    private val userProfileDao: UserProfileDao,
) {
    fun observeSettings(): Flow<SettingsEntity?> = settingsDao.observe()

    fun observeProfile(): Flow<UserProfileEntity?> = userProfileDao.observeProfile()
}
