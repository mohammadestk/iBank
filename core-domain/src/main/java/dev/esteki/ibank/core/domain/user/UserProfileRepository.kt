package dev.esteki.ibank.core.domain.user

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.user.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<Result<UserProfile>>
    fun getNotificationCount(): Flow<Result<Int>>
}
