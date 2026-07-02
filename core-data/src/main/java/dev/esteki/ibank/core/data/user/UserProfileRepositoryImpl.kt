package dev.esteki.ibank.core.data.user

import dev.esteki.ibank.core.data.db.mapper.toDomain
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.user.UserProfile
import dev.esteki.ibank.core.domain.user.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
) : UserProfileRepository {

    override fun getUserProfile(): Flow<Result<UserProfile>> =
        localDataSource.observeProfile().map { entity ->
            if (entity != null) {
                Result.Success(entity.toDomain())
            } else {
                Result.Failure(AppError.NotFound)
            }
        }

    override fun getNotificationCount(): Flow<Result<Int>> =
        localDataSource.observeProfile().map { entity ->
            if (entity != null) {
                Result.Success(entity.notificationCount)
            } else {
                Result.Success(0)
            }
        }
}
