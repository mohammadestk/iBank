package dev.esteki.ibank.core.domain.settings.model

data class SettingsItem(
    val id: String,
    val label: String,
    val iconRes: Int,
    val type: SettingsItemType,
    val subtitle: String? = null,
    val isChecked: Boolean = false,
)
