package dev.esteki.ibank.core.data.settings

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.model.Settings
import dev.esteki.ibank.core.domain.model.SettingsItem
import dev.esteki.ibank.core.domain.model.SettingsItemType
import dev.esteki.ibank.core.domain.model.SettingsSection
import dev.esteki.ibank.core.domain.model.UserProfile
import dev.esteki.ibank.core.domain.settings.SettingsRepository
import dev.esteki.ibank.core.presentation.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeSettingsRepository @Inject constructor() : SettingsRepository {

    override fun getSettings(): Flow<Result<Settings>> = flowOf(
        Result.Success(
            Settings(
                profile = UserProfile(
                    id = "1",
                    name = "Mohammad Esteki",
                    avatarUrl = "",
                    notificationCount = 5,
                ),
                sections = listOf(
                    SettingsSection(
                        id = "security",
                        title = "Security",
                        items = listOf(
                            SettingsItem("biometric", "Biometric login", R.drawable.ic_send, SettingsItemType.TOGGLE, subtitle = "Face ID enabled", isChecked = true),
                            SettingsItem("2fa", "Two-factor authentication", R.drawable.ic_transfer, SettingsItemType.NAVIGATION),
                            SettingsItem("pin", "Change PIN", R.drawable.ic_request, SettingsItemType.NAVIGATION),
                        ),
                    ),
                    SettingsSection(
                        id = "preferences",
                        title = "Preferences",
                        items = listOf(
                            SettingsItem("notifications", "Notifications", R.drawable.ic_paybill, SettingsItemType.NAVIGATION),
                            SettingsItem("language", "Language", R.drawable.ic_topup, SettingsItemType.NAVIGATION, subtitle = "English (US)"),
                        ),
                    ),
                ),
            )
        )
    )
}
