package dev.esteki.ibank.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.esteki.ibank.BottomRoute
import dev.esteki.ibank.feature.home.HomeScreen
import dev.esteki.ibank.feature.message.MessageScreen
import dev.esteki.ibank.feature.search.SearchScreen
import dev.esteki.ibank.feature.settings.SettingsScreen

@Composable
fun AppNavDisplay(
    modifier: Modifier = Modifier,
    backStack: SnapshotStateList<BottomRoute>
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<BottomRoute.Home> { key ->
                HomeScreen(modifier = Modifier.fillMaxSize())
            }
            entry<BottomRoute.Search> { key ->
                SearchScreen(modifier = Modifier.fillMaxSize())
            }
            entry<BottomRoute.Message> { key ->
                MessageScreen(modifier = Modifier.fillMaxSize())
            }
            entry<BottomRoute.Settings> { key ->
                SettingsScreen(modifier = Modifier.fillMaxSize())
            }
        },
    )
}