package dev.esteki.ibank.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth
import dev.esteki.ibank.BottomRoute
import org.junit.Rule
import org.junit.Test



class AppBottomBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testItems = listOf(
        BottomRoute.Home,
        BottomRoute.Search,
        BottomRoute.Message,
        BottomRoute.Settings,
    )

    @Test
    fun allItemsAreDisplayed() {
        composeTestRule.setContent {
            AppBottomBar(
                selectedRoute = BottomRoute.Home,
                onItemClick = {},
                items = testItems
            )
        }

        testItems.forEach { item ->
            composeTestRule.onNodeWithText(item.label).assertIsDisplayed()
        }
    }

    @Test
    fun correctItemIsSelectedInitially() {
        composeTestRule.setContent {
            AppBottomBar(
                selectedRoute = BottomRoute.Search,
                onItemClick = {},
                items = testItems
            )
        }

        composeTestRule.onNodeWithText(BottomRoute.Home.label).assertIsNotSelected()
        composeTestRule.onNodeWithText(BottomRoute.Search.label).assertIsSelected()
        composeTestRule.onNodeWithText(BottomRoute.Message.label).assertIsNotSelected()
        composeTestRule.onNodeWithText(BottomRoute.Settings.label).assertIsNotSelected()
    }

    @Test
    fun clickingItemInvokesCallbackWithCorrectRoute() {
        var clickedRoute: BottomRoute? = null

        composeTestRule.setContent {
            AppBottomBar(
                selectedRoute = BottomRoute.Home,
                onItemClick = { clickedRoute = it },
                items = testItems
            )
        }

        composeTestRule.onNodeWithText(BottomRoute.Settings.label).performClick()

        Truth.assertThat(clickedRoute).isSameInstanceAs(BottomRoute.Settings)
    }

    @Test
    fun clickingAlreadySelectedItemStillInvokesCallback() {
        var clickCount = 0

        composeTestRule.setContent {
            AppBottomBar(
                selectedRoute = BottomRoute.Home,
                onItemClick = { clickCount++ },
                items = testItems
            )
        }

        composeTestRule.onNodeWithText(BottomRoute.Home.label).performClick()

        Truth.assertThat(clickCount).isEqualTo(1)
    }

    @Test
    fun contentDescriptionsAreSetCorrectly() {
        composeTestRule.setContent {
            AppBottomBar(
                selectedRoute = BottomRoute.Home,
                onItemClick = {},
                items = testItems
            )
        }

        testItems.forEach { item ->
            composeTestRule.onNodeWithContentDescription(
                item.contentDescription,
                useUnmergedTree = true
            ).assertExists()
        }
    }

    @Test
    fun rendersCorrectlyWithSubsetOfItems() {
        val subset = listOf(BottomRoute.Home, BottomRoute.Settings)

        composeTestRule.setContent {
            AppBottomBar(
                selectedRoute = BottomRoute.Home,
                onItemClick = {},
                items = subset
            )
        }

        composeTestRule.onNodeWithText(BottomRoute.Home.label).assertIsDisplayed()
        composeTestRule.onNodeWithText(BottomRoute.Settings.label).assertIsDisplayed()
        composeTestRule.onNodeWithText(BottomRoute.Search.label).assertDoesNotExist()
        composeTestRule.onNodeWithText(BottomRoute.Message.label).assertDoesNotExist()
    }
}