package dev.esteki.ibank.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.esteki.ibank.ui.theme.IBankTheme

@Composable
fun MainView() {
    val backStack = remember { mutableStateListOf<BottomRoute>(BottomDestinations.first()) }
    var selectedRoute by rememberSaveable { mutableStateOf(BottomDestinations.first()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(
                modifier = Modifier.fillMaxWidth(),
                selectedRoute = selectedRoute,
                onItemClick = { route ->
                    backStack.removeLastOrNull()
                    backStack.add(route)
                    selectedRoute = route
                },
                items = BottomDestinations
            )
        },
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
}

@Preview
@Composable
private fun MainViewPreview() {
    IBankTheme {
        MainView()
    }
}