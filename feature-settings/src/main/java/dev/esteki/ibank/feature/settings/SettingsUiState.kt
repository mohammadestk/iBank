package dev.esteki.ibank.feature.settings

import androidx.compose.runtime.Stable
import dev.esteki.ibank.core.domain.settings.model.SettingsSection
import dev.esteki.ibank.core.domain.user.model.UserProfile

@Stable
data class SettingsUiState(
    val result: SettingsResult,
    val profile: UserProfile?,
    val sections: List<SettingsSection>,
)
