package dev.esteki.ibank.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.esteki.ibank.BottomRoute
import dev.esteki.ibank.feature.home.HomeScreen

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
                Text(text = key.label)
            }
            entry<BottomRoute.Message> { key ->
                Text(text = key.label)
            }
            entry<BottomRoute.Settings> { key ->
                Text(text = key.label)
            }
        },
    )
}