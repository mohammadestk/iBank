package dev.esteki.ibank.feature.settings.viewmodel

import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.settings.model.Settings
import dev.esteki.ibank.core.domain.settings.model.SettingsItem
import dev.esteki.ibank.core.domain.settings.model.SettingsItemType
import dev.esteki.ibank.core.domain.settings.model.SettingsSection
import dev.esteki.ibank.core.domain.settings.usecase.GetSettingsUseCase
import dev.esteki.ibank.core.domain.user.model.UserProfile
import dev.esteki.ibank.feature.settings.model.SettingsResult
import dev.esteki.ibank.feature.settings.model.SettingsUiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getSettingsUseCase: GetSettingsUseCase

    private val settings = Settings(
        profile = UserProfile("1", "John", "url", 5),
        sections = listOf(
            SettingsSection("sec1", "Security", listOf(
                SettingsItem("item1", "Biometric", 123, SettingsItemType.TOGGLE, "Face ID", true),
                SettingsItem("item2", "2FA", 456, SettingsItemType.NAVIGATION),
            )),
            SettingsSection("sec2", "Preferences", listOf(
                SettingsItem("item3", "Notifications", 789, SettingsItemType.NAVIGATION),
            )),
        ),
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getSettingsUseCase = mockk()
        every { getSettingsUseCase() } returns flowOf(Result.Success(settings))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = SettingsViewModel(
        initialUiState = SettingsUiState(
            result = SettingsResult.Idle,
            profile = null,
            sections = emptyList(),
        ),
        getSettingsUseCase = getSettingsUseCase,
    )

    @Test
    fun `loadSettings succeeds with settings data`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(SettingsResult.Success::class.java)
        assertThat(state.profile?.name).isEqualTo("John")
        assertThat(state.sections).hasSize(2)
        assertThat(state.sections[0].items).hasSize(2)
    }

    @Test
    fun `loadSettings failure shows Failure state`() = runTest {
        every { getSettingsUseCase() } returns flowOf(Result.Failure(AppError.NotFound))

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.result).isInstanceOf(SettingsResult.Failure::class.java)
        assertThat((state.result as SettingsResult.Failure).error).isEqualTo(AppError.NotFound)
    }

    @Test
    fun `settings has correct section structure`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.sections[0].id).isEqualTo("sec1")
        assertThat(state.sections[0].title).isEqualTo("Security")
        assertThat(state.sections[0].items[0].type).isEqualTo(SettingsItemType.TOGGLE)
        assertThat(state.sections[0].items[0].isChecked).isTrue()
        assertThat(state.sections[1].id).isEqualTo("sec2")
    }

    @Test
    fun `settings profile contains notification count`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.profile?.notificationCount).isEqualTo(5)
    }
}
