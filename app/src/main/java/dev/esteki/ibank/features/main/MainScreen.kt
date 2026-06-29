package dev.esteki.ibank.features.main

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
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.esteki.ibank.BottomDestinations
import dev.esteki.ibank.BottomRoute
import dev.esteki.ibank.components.AppBottomBar
import dev.esteki.ibank.components.AppNavDisplay

@Composable
fun MainScreen() {
    val backStack = remember { mutableStateListOf<BottomRoute>(BottomDestinations.first()) }
    var selectedRoute by rememberSerializable { mutableStateOf(BottomDestinations.first()) }

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