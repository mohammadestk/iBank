package dev.esteki.ibank.feature.search.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.model.AccountType
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.model.TransactionIcon
import dev.esteki.ibank.feature.search.model.SearchFilter
import dev.esteki.ibank.core.presentation.theme.IBankTheme
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchTopBar_displaysTitle() {
        composeTestRule.setContent {
            IBankTheme {
                SearchTopBar()
            }
        }

        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
    }

    @Test
    fun searchBar_displaysPlaceholder() {
        composeTestRule.setContent {
            IBankTheme {
                SearchBar(
                    query = "",
                    onQueryChange = {},
                    onClearClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Search transactions, payees, help").assertIsDisplayed()
    }

    @Test
    fun filterChipRow_displaysAllFilters() {
        composeTestRule.setContent {
            IBankTheme {
                FilterChipRow(
                    selectedFilter = SearchFilter.ALL,
                    onFilterSelected = {},
                )
            }
        }

        composeTestRule.onNodeWithText("All").assertIsDisplayed()
        composeTestRule.onNodeWithText("Payees").assertIsDisplayed()
        composeTestRule.onNodeWithText("Transactions").assertIsDisplayed()
        composeTestRule.onNodeWithText("Help").assertIsDisplayed()
    }

    @Test
    fun searchResults_displaysAccounts() {
        val accounts = listOf(
            Account("1", "Main Account", 24318.52, "USD", "****1234", AccountType.SAVINGS),
        )

        composeTestRule.setContent {
            IBankTheme {
                SearchResults(
                    accounts = accounts,
                    transactions = emptyList(),
                )
            }
        }

        composeTestRule.onNodeWithText("Main Account").assertIsDisplayed()
    }

    @Test
    fun searchResults_displaysTransactions() {
        val transactions = listOf(
            Transaction("1", "Spotify", "Subscription", -10.99, false, TransactionIcon.MUSIC, "Today"),
        )

        composeTestRule.setContent {
            IBankTheme {
                SearchResults(
                    accounts = emptyList(),
                    transactions = transactions,
                )
            }
        }

        composeTestRule.onNodeWithText("Spotify").assertIsDisplayed()
    }
}
