package dev.esteki.ibank.feature.settings

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.common.toUserMessage
import dev.esteki.ibank.core.domain.model.Settings
import dev.esteki.ibank.core.domain.model.SettingsItem
import dev.esteki.ibank.core.domain.model.SettingsSection
import dev.esteki.ibank.core.domain.model.UserProfile
import dev.esteki.ibank.core.domain.settings.GetSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Stable
data class SettingsUiState(
    val result: SettingsResult,
    val profile: UserProfile?,
    val sections: List<SettingsSection>,
)

sealed interface SettingsResult {
    data object Idle : SettingsResult
    data object Loading : SettingsResult
    data class Success(val settings: Settings) : SettingsResult
    data class Failure(val error: AppError, val message: String) : SettingsResult
}

sealed interface SettingsIntent {
    data object LoadSettings : SettingsIntent
    data class ItemClicked(val item: SettingsItem) : SettingsIntent
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val initialUiState: SettingsUiState,
    private val getSettingsUseCase: GetSettingsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        onIntent(SettingsIntent.LoadSettings)
    }

    fun onIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.LoadSettings -> loadSettings()
            is SettingsIntent.ItemClicked -> handleItemClick(intent.item)
        }
    }

    private fun loadSettings() {
        _uiState.update { it.copy(result = SettingsResult.Loading) }

        getSettingsUseCase()
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        val settings = result.data
                        _uiState.update {
                            it.copy(
                                result = SettingsResult.Success(settings),
                                profile = settings.profile,
                                sections = settings.sections,
                            )
                        }
                    }
                    is Result.Failure -> {
                        _uiState.update {
                            it.copy(
                                result = SettingsResult.Failure(
                                    error = result.error,
                                    message = result.error.toUserMessage(),
                                ),
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleItemClick(item: SettingsItem) {
        // TODO: Navigate to item detail
    }
}
