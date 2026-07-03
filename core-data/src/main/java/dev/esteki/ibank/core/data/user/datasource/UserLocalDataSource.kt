package dev.esteki.ibank.core.data.user.datasource

import dev.esteki.ibank.core.data.user.dao.UserProfileDao
import dev.esteki.ibank.core.data.user.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class UserLocalDataSource(
    private val userProfileDao: UserProfileDao,
) {
    fun observeProfile(): Flow<UserProfileEntity?> = userProfileDao.observeProfile()
}
