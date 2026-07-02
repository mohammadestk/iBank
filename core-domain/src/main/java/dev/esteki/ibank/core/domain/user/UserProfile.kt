package dev.esteki.ibank.core.domain.user

data class UserProfile(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val notificationCount: Int,
)
