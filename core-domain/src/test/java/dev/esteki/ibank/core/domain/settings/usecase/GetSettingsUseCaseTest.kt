package dev.esteki.ibank.core.domain.settings.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.settings.model.Settings
import dev.esteki.ibank.core.domain.settings.model.SettingsItemType
import dev.esteki.ibank.core.domain.settings.model.SettingsItem
import dev.esteki.ibank.core.domain.settings.model.SettingsSection
import dev.esteki.ibank.core.domain.settings.repository.SettingsRepository
import dev.esteki.ibank.core.domain.user.model.UserProfile
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSettingsUseCaseTest {

    private lateinit var repository: SettingsRepository
    private lateinit var useCase: GetSettingsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetSettingsUseCase(repository)
    }

    @Test
    fun `invoke returns settings from repository`() = runTest {
        // Arrange
        val settings = Settings(
            profile = UserProfile("1", "John", "url", 5),
            sections = listOf(
                SettingsSection("sec1", "Security", listOf(
                    SettingsItem("item1", "Biometric", 123, SettingsItemType.TOGGLE),
                )),
            ),
        )
        every { repository.getSettings() } returns flowOf(Result.Success(settings))

        // Act & Assert
        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(settings)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        every { repository.getSettings() } returns flowOf(Result.Failure(AppError.NotFound))

        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.NotFound)
            awaitComplete()
        }
    }
}
