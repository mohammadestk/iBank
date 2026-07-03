package dev.esteki.ibank.core.data.settings.entity

import kotlinx.serialization.Serializable

@Serializable
data class SettingsItemDto(
    val id: String,
    val label: String,
    val iconRes: Int,
    val type: String,
    val subtitle: String? = null,
    val isChecked: Boolean = false,
)
