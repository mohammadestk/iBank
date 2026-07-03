package dev.esteki.ibank.core.data.user.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val id: String,
    val name: String,
    val avatarUrl: String,
    val notificationCount: Int,
)
