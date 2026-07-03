package dev.esteki.ibank.feature.settings.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dev.esteki.ibank.core.domain.settings.model.SettingsItem
import dev.esteki.ibank.core.domain.settings.model.SettingsItemType
import dev.esteki.ibank.core.domain.settings.model.SettingsSection
import dev.esteki.ibank.core.domain.user.model.UserProfile
import dev.esteki.ibank.core.presentation.R
import dev.esteki.ibank.core.presentation.theme.IBankTheme
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val profile = UserProfile("1", "John", "url", 5)
    private val sections = listOf(
        SettingsSection(
            "sec1", "Security", listOf(
                SettingsItem(
                    "item1",
                    "Biometric login",
                    R.drawable.ic_ring,
                    SettingsItemType.TOGGLE,
                    "Face ID enabled",
                    true
                ),
                SettingsItem(
                    "item2",
                    "Two-factor authentication",
                    R.drawable.ic_ring,
                    SettingsItemType.NAVIGATION
                ),
            )
        ),
        SettingsSection(
            "sec2", "Preferences", listOf(
                SettingsItem(
                    "item3",
                    "Notifications",
                    R.drawable.ic_ring,
                    SettingsItemType.NAVIGATION
                ),
                SettingsItem(
                    "item4",
                    "Language",
                    R.drawable.ic_ring,
                    SettingsItemType.NAVIGATION,
                    "English (US)"
                ),
            )
        ),
    )

    @Test
    fun settingsContent_displaysTitle() {
        composeTestRule.setContent {
            IBankTheme {
                SettingsContent(
                    profile = profile,
                    sections = sections,
                    onItemClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
    }

    @Test
    fun settingsContent_displaysProfileName() {
        composeTestRule.setContent {
            IBankTheme {
                SettingsContent(
                    profile = profile,
                    sections = sections,
                    onItemClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("John").assertIsDisplayed()
    }

    @Test
    fun settingsContent_displaysSectionTitles() {
        composeTestRule.setContent {
            IBankTheme {
                SettingsContent(
                    profile = profile,
                    sections = sections,
                    onItemClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Security", ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Preferences", ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun settingsContent_displaysItemLabels() {
        composeTestRule.setContent {
            IBankTheme {
                SettingsContent(
                    profile = profile,
                    sections = sections,
                    onItemClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Biometric login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Two-factor authentication").assertIsDisplayed()
        composeTestRule.onNodeWithText("Notifications").assertIsDisplayed()
        composeTestRule.onNodeWithText("Language").assertIsDisplayed()
    }

    @Test
    fun settingsContent_displaysSubtitles() {
        composeTestRule.setContent {
            IBankTheme {
                SettingsContent(
                    profile = profile,
                    sections = sections,
                    onItemClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Face ID enabled").assertIsDisplayed()
        composeTestRule.onNodeWithText("English (US)").assertIsDisplayed()
    }

    @Test
    fun settingsContent_displaysSignOutButton() {
        composeTestRule.setContent {
            IBankTheme {
                SettingsContent(
                    profile = profile,
                    sections = sections,
                    onItemClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Sign out").assertIsDisplayed()
    }
}
