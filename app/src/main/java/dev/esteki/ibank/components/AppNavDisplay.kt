package dev.esteki.ibank.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.esteki.ibank.BottomRoute
import dev.esteki.ibank.feature.home.ui.HomeScreen
import dev.esteki.ibank.feature.message.ui.MessageScreen
import dev.esteki.ibank.feature.search.ui.SearchScreen
import dev.esteki.ibank.feature.settings.ui.SettingsScreen

@Composable
fun AppNavDisplay(
    modifier: Modifier = Modifier,
    backStack: SnapshotStateList<BottomRoute>
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberViewModelStoreNavEntryDecorator(),
            rememberSaveableStateHolderNavEntryDecorator()
        ),
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