package dev.esteki.ibank.core.data.user

import dev.esteki.ibank.core.data.user.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class UserLocalDataSource(
    private val userProfileDao: UserProfileDao,
) {
    fun observeProfile(): Flow<UserProfileEntity?> = userProfileDao.observeProfile()
}
