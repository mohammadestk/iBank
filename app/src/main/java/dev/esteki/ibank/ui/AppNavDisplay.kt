package dev.esteki.ibank.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

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
                Text(text = key.label)
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