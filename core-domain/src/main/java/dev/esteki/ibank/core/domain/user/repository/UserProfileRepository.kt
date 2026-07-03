package dev.esteki.ibank.core.domain.user.repository

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.user.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(): Flow<Result<UserProfile>>
    fun getNotificationCount(): Flow<Result<Int>>
}
