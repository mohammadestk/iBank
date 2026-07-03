package dev.esteki.ibank.core.domain.settings.model

data class SettingsSection(
    val id: String,
    val title: String,
    val items: List<SettingsItem>,
)
