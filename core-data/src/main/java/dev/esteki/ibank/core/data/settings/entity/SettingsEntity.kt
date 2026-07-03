package dev.esteki.ibank.core.data.settings.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: String = "default",
    val profileId: String,
    val sectionsJson: String,
)
