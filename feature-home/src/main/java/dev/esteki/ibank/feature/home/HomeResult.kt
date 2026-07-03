package dev.esteki.ibank.feature.home

import dev.esteki.ibank.core.domain.common.AppError

sealed interface HomeResult {
    data object Idle : HomeResult
    data object Loading : HomeResult
    data class Success(
        val userName: String,
        val avatarUrl: String,
        val notificationCount: Int,
        val totalBalance: Double,
    ) : HomeResult
    data class Failure(val error: AppError, val message: String) : HomeResult
}
