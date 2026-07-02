package dev.esteki.ibank.core.data.local

import dev.esteki.ibank.core.data.local.dao.SettingsDao
import dev.esteki.ibank.core.data.local.dao.UserProfileDao
import dev.esteki.ibank.core.data.local.entity.SettingsEntity
import dev.esteki.ibank.core.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class SettingsLocalDataSource(
    private val settingsDao: SettingsDao,
    private val userProfileDao: UserProfileDao,
) {
    fun observeSettings(): Flow<SettingsEntity?> = settingsDao.observe()

    fun observeProfile(): Flow<UserProfileEntity?> = userProfileDao.observeProfile()
}
