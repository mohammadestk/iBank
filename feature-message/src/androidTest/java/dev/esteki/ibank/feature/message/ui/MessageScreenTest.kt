package dev.esteki.ibank.feature.message.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dev.esteki.ibank.core.domain.message.model.Message
import dev.esteki.ibank.core.domain.message.model.MessageType
import dev.esteki.ibank.feature.message.model.MessageFilter
import dev.esteki.ibank.core.presentation.theme.IBankTheme
import org.junit.Rule
import org.junit.Test

class MessageScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun segmentedFilterRow_displaysAllFilters() {
        composeTestRule.setContent {
            IBankTheme {
                SegmentedFilterRow(
                    selectedFilter = MessageFilter.INBOX,
                    onFilterSelected = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Inbox").assertIsDisplayed()
        composeTestRule.onNodeWithText("Alerts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Support").assertIsDisplayed()
    }

    @Test
    fun messageList_displaysMessages() {
        val messages = listOf(
            Message("1", "Payment", "You received $100", "2 min ago", MessageType.TRANSACTION, false),
            Message("2", "Alert", "New login detected", "1 hour ago", MessageType.SECURITY, true),
        )

        composeTestRule.setContent {
            IBankTheme {
                MessageList(
                    messages = messages,
                    onMessageClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Payment").assertIsDisplayed()
        composeTestRule.onNodeWithText("Alert").assertIsDisplayed()
    }

    @Test
    fun messageList_displaysMessageDescriptions() {
        val messages = listOf(
            Message("1", "Payment", "You received $100", "2 min ago", MessageType.TRANSACTION, false),
        )

        composeTestRule.setContent {
            IBankTheme {
                MessageList(
                    messages = messages,
                    onMessageClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("You received $100").assertIsDisplayed()
    }

    @Test
    fun messageList_displaysTimestamps() {
        val messages = listOf(
            Message("1", "Payment", "Desc", "2 min ago", MessageType.TRANSACTION, false),
        )

        composeTestRule.setContent {
            IBankTheme {
                MessageList(
                    messages = messages,
                    onMessageClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("2 min ago").assertIsDisplayed()
    }
}
