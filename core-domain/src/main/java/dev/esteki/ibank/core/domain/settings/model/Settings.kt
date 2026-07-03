package dev.esteki.ibank.core.domain.settings.model

import dev.esteki.ibank.core.domain.user.model.UserProfile

data class Settings(
    val profile: UserProfile,
    val sections: List<SettingsSection>,
)
