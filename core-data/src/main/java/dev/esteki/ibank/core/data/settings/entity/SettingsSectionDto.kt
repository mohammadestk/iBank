package dev.esteki.ibank.core.data.settings.entity

import kotlinx.serialization.Serializable

@Serializable
data class SettingsSectionDto(
    val id: String,
    val title: String,
    val items: List<SettingsItemDto>,
)
