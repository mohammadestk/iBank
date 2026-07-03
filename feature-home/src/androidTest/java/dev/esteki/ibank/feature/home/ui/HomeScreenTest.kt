package dev.esteki.ibank.feature.home.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.model.AccountType
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.quickaction.model.QuickActionType
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.model.TransactionIcon
import dev.esteki.ibank.core.domain.user.model.UserProfile
import dev.esteki.ibank.core.presentation.R
import dev.esteki.ibank.feature.home.model.HomeResult
import dev.esteki.ibank.feature.home.model.HomeUiState
import dev.esteki.ibank.core.presentation.theme.IBankTheme
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val profile = UserProfile(UUID.randomUUID().toString(), "John", "url", 5)
    private val accounts = listOf(
        Account(UUID.randomUUID().toString(), "Main Account", 24318.52, "USD", "****1234", AccountType.SAVINGS),
    )
    private val quickActions = listOf(
        QuickAction(UUID.randomUUID().toString(), "Send", R.drawable.ic_ring, QuickActionType.SEND),
        QuickAction(UUID.randomUUID().toString(), "Request", R.drawable.ic_ring, QuickActionType.RECEIVE),
    )
    private val transactions = listOf(
        Transaction(UUID.randomUUID().toString(), "Spotify", "Subscription", -10.99, false, TransactionIcon.MUSIC, "Today"),
        Transaction(UUID.randomUUID().toString(), "Salary", "Deposit", 4200.0, true, TransactionIcon.SALARY, "Yesterday"),
    )

    @Test
    fun homeContent_displaysUserName() {
        val state = HomeUiState(
            result = HomeResult.Success("John", "url", 5, 24318.52),
            accounts = accounts,
            quickActions = quickActions,
            transactions = transactions,
        )

        composeTestRule.setContent {
            IBankTheme {
                HomeContent(
                    uiState = state,
                    result = state.result as HomeResult.Success,
                    onQuickActionClick = {},
                    onNotificationClick = {},
                    onSeeAllClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("John").assertIsDisplayed()
    }

    @Test
    fun homeContent_displaysTotalBalance() {
        val state = HomeUiState(
            result = HomeResult.Success("John", "url", 5, 24318.52),
            accounts = accounts,
            quickActions = quickActions,
            transactions = transactions,
        )

        composeTestRule.setContent {
            IBankTheme {
                HomeContent(
                    uiState = state,
                    result = state.result as HomeResult.Success,
                    onQuickActionClick = {},
                    onNotificationClick = {},
                    onSeeAllClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("TOTAL BALANCE").assertIsDisplayed()
    }

    @Test
    fun homeContent_displaysQuickActions() {
        val state = HomeUiState(
            result = HomeResult.Success("John", "url", 5, 24318.52),
            accounts = accounts,
            quickActions = quickActions,
            transactions = transactions,
        )

        composeTestRule.setContent {
            IBankTheme {
                HomeContent(
                    uiState = state,
                    result = state.result as HomeResult.Success,
                    onQuickActionClick = {},
                    onNotificationClick = {},
                    onSeeAllClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Send").assertIsDisplayed()
        composeTestRule.onNodeWithText("Request").assertIsDisplayed()
    }

    @Test
    fun homeContent_displaysTransactions() {
        val state = HomeUiState(
            result = HomeResult.Success("John", "url", 5, 24318.52),
            accounts = accounts,
            quickActions = quickActions,
            transactions = transactions,
        )

        composeTestRule.setContent {
            IBankTheme {
                HomeContent(
                    uiState = state,
                    result = state.result as HomeResult.Success,
                    onQuickActionClick = {},
                    onNotificationClick = {},
                    onSeeAllClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Spotify").assertIsDisplayed()
        composeTestRule.onNodeWithText("Salary").assertIsDisplayed()
    }

    @Test
    fun homeContent_displaysSectionHeaders() {
        val state = HomeUiState(
            result = HomeResult.Success("John", "url", 5, 24318.52),
            accounts = accounts,
            quickActions = quickActions,
            transactions = transactions,
        )

        composeTestRule.setContent {
            IBankTheme {
                HomeContent(
                    uiState = state,
                    result = state.result as HomeResult.Success,
                    onQuickActionClick = {},
                    onNotificationClick = {},
                    onSeeAllClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Recent activity").assertIsDisplayed()
        composeTestRule.onNodeWithText("See all").assertIsDisplayed()
    }
}
