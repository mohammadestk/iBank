package dev.esteki.ibank.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        AppNavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            backStack = backStack
        )
    }
}