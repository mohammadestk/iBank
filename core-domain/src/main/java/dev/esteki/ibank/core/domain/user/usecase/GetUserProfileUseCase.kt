package dev.esteki.ibank.core.domain.user.usecase

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.user.model.UserProfile
import dev.esteki.ibank.core.domain.user.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository,
) {
    operator fun invoke(): Flow<Result<UserProfile>> = repository.getUserProfile()
}
