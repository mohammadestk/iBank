package dev.esteki.ibank.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.esteki.ibank.BottomDestinations
import dev.esteki.ibank.components.AppBottomBar
import dev.esteki.ibank.components.AppNavDisplay

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(
                modifier = Modifier.fillMaxWidth(),
                selectedRoute = uiState.selectedRoute,
                onItemClick = viewModel::onRouteSelected,
                items = BottomDestinations
            )
        },
    ) { innerPadding ->
        AppNavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            backStack = uiState.backStack
        )
    }
}