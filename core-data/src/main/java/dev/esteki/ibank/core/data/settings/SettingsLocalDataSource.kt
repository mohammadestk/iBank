package dev.esteki.ibank.core.data.settings

import dev.esteki.ibank.core.data.user.UserProfileDao
import dev.esteki.ibank.core.data.user.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class SettingsLocalDataSource(
    private val settingsDao: SettingsDao,
    private val userProfileDao: UserProfileDao,
) {
    fun observeSettings(): Flow<SettingsEntity?> = settingsDao.observe()

    fun observeProfile(): Flow<UserProfileEntity?> = userProfileDao.observeProfile()
}
