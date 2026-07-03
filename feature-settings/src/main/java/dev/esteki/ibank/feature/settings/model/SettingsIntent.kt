package dev.esteki.ibank.feature.settings.model

import dev.esteki.ibank.core.domain.settings.model.SettingsItem

sealed interface SettingsIntent {
    data object LoadSettings : SettingsIntent
    data class ItemClicked(val item: SettingsItem) : SettingsIntent
}
