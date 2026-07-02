package dev.esteki.ibank.core.data.local

import kotlinx.serialization.Serializable

@Serializable
data class SettingsSectionDto(
    val id: String,
    val title: String,
    val items: List<SettingsItemDto>,
)

@Serializable
data class SettingsItemDto(
    val id: String,
    val label: String,
    val iconRes: Int,
    val type: String,
    val subtitle: String? = null,
    val isChecked: Boolean = false,
)
