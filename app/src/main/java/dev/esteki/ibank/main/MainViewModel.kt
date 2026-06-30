package dev.esteki.ibank.main

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteki.ibank.BottomRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class MainUiState(
    val destinations: List<BottomRoute>,
    val selectedRoute: BottomRoute,
    val backStack: SnapshotStateList<BottomRoute>,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    initialUiState: MainUiState,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            // TODO: Replace with actual data fetching (repository / use case)
            val destinations = listOf(
                BottomRoute.Home,
                BottomRoute.Search,
                BottomRoute.Message,
                BottomRoute.Settings,
            )
            _uiState.update { state ->
                state.backStack.add(destinations.first())
                state.copy(
                    destinations = destinations,
                    selectedRoute = destinations.first(),
                )
            }
        }
    }

    fun onRouteSelected(route: BottomRoute) {
        _uiState.update { state ->
            state.backStack.removeLastOrNull()
            state.backStack.add(route)
            state.copy(selectedRoute = route)
        }
    }
}
