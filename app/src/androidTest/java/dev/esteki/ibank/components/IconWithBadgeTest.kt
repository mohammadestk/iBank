package dev.esteki.ibank.components

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.esteki.ibank.R
import org.junit.Rule
import org.junit.Test

class IconWithBadgeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun badgeCount_displaysCorrectNumber() {
        composeTestRule.setContent {
            IconWithBadge(
                icon = R.drawable.ring,
                badgeCount = 7,
                contentDescription = "Notifications"
            )
        }

        composeTestRule.onNodeWithText("7").assertIsDisplayed()
    }

    @Test
    fun badgeCount_over99_showsCappedLabel() {
        composeTestRule.setContent {
            IconWithBadge(
                icon = R.drawable.ring,
                badgeCount = 150,
                contentDescription = "Notifications"
            )
        }

        composeTestRule.onNodeWithText("99+").assertIsDisplayed()
        composeTestRule.onNodeWithText("150").assertDoesNotExist()
    }

    @Test
    fun badgeCount_zero_doesNotShowBadge() {
        composeTestRule.setContent {
            IconWithBadge(
                icon = R.drawable.ring,
                badgeCount = 0,
                contentDescription = "Notifications"
            )
        }

        // Icon should exist, but no numeric badge text should render
        composeTestRule.onNodeWithContentDescription("Notifications").assertIsDisplayed()
        composeTestRule.onNodeWithText("0").assertDoesNotExist()
    }

    @Test
    fun badgeCount_null_showsDotIndicator_noText() {
        composeTestRule.setContent {
            IconWithBadge(
                icon = R.drawable.ring,
                badgeCount = null,
                contentDescription = "Notifications"
            )
        }

        // Dot badge has no text content — assert icon renders and no stray text node exists.
        composeTestRule.onNodeWithContentDescription("Notifications").assertIsDisplayed()
        composeTestRule.onAllNodes(hasTextExactly("0")).assertCountEquals(0)
    }

    @Test
    fun click_invokesCallback() {
        var clicked = false

        composeTestRule.setContent {
            IconWithBadge(
                icon = R.drawable.ring,
                badgeCount = 3,
                contentDescription = "Notifications",
                onClick = { clicked = true }
            )
        }

        composeTestRule.onNodeWithContentDescription("Notifications").performClick()

        assert(clicked) { "Expected onClick to be invoked" }
    }

    @Test
    fun withoutOnClick_isNotClickable() {
        composeTestRule.setContent {
            IconWithBadge(
                icon = R.drawable.ring,
                badgeCount = 3,
                contentDescription = "Notifications",
                onClick = null
            )
        }

        composeTestRule.onNodeWithContentDescription("Notifications")
            .assertHasNoClickAction()
    }

    @Test
    fun contentDescription_isAppliedToIcon() {
        composeTestRule.setContent {
            IconWithBadge(
                icon = R.drawable.ring,
                badgeCount = 5,
                contentDescription = "Notifications"
            )
        }

        composeTestRule.onNodeWithContentDescription("Notifications").assertExists()
    }
}