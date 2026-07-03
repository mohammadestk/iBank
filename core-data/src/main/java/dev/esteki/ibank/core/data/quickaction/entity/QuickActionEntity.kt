package dev.esteki.ibank.core.data.quickaction.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quick_actions")
data class QuickActionEntity(
    @PrimaryKey val id: String,
    val label: String,
    val iconRes: Int,
    val type: String,
)
