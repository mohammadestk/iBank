package dev.esteki.ibank.core.domain.settings

import dev.esteki.ibank.core.domain.user.UserProfile

data class Settings(
    val profile: UserProfile,
    val sections: List<SettingsSection>,
)

data class SettingsSection(
    val id: String,
    val title: String,
    val items: List<SettingsItem>,
)

data class SettingsItem(
    val id: String,
    val label: String,
    val iconRes: Int,
    val type: SettingsItemType,
    val subtitle: String? = null,
    val isChecked: Boolean = false,
)

enum class SettingsItemType {
    NAVIGATION,
    TOGGLE,
    INFO,
}
