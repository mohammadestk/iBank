package dev.esteki.ibank.feature.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class HomeUiState(
    val avatar: String,
    val title: String,
    val notificationCount: Int,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    initialUiState: HomeUiState,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            // TODO: Replace with actual data fetching (repository / use case)
            _uiState.update {
                it.copy(
                    avatar = "https://picsum.photos/200",
                    title = "Hi, Mohammad Esteki!",
                    notificationCount = 5,
                )
            }
        }
    }
}
