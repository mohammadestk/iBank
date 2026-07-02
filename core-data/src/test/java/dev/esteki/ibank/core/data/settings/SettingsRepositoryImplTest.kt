package dev.esteki.ibank.core.data.settings

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.user.UserProfileEntity
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.settings.SettingsItemType
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class SettingsRepositoryImplTest {

    private lateinit var localDataSource: SettingsLocalDataSource
    private lateinit var repository: SettingsRepositoryImpl
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setup() {
        localDataSource = mockk()
        repository = SettingsRepositoryImpl(localDataSource)
    }

    @Test
    fun `getSettings emits success with parsed JSON`() = runTest {
        // Arrange
        val profile = UserProfileEntity("1", "John", "url", 5)
        val sections = listOf(
            SettingsSectionDto(
                id = "security",
                title = "Security",
                items = listOf(
                    SettingsItemDto("biometric", "Biometric", 123, "TOGGLE", "Face ID", true),
                ),
            ),
        )
        val settings = SettingsEntity("default", "1", json.encodeToString(sections))
        every { localDataSource.observeProfile() } returns flowOf(profile)
        every { localDataSource.observeSettings() } returns flowOf(settings)

        // Act
        repository.getSettings().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val data = (result as Result.Success).data
            assertThat(data.profile.name).isEqualTo("John")
            assertThat(data.sections).hasSize(1)
            assertThat(data.sections[0].id).isEqualTo("security")
            assertThat(data.sections[0].items[0].type).isEqualTo(SettingsItemType.TOGGLE)
            awaitComplete()
        }
    }

    @Test
    fun `getSettings emits failure when profile is null`() = runTest {
        // Arrange
        val sections = listOf(SettingsSectionDto("sec", "Security", emptyList()))
        val settings = SettingsEntity("default", "1", json.encodeToString(sections))
        every { localDataSource.observeProfile() } returns flowOf(null)
        every { localDataSource.observeSettings() } returns flowOf(settings)

        // Act
        repository.getSettings().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.NotFound)
            awaitComplete()
        }
    }

    @Test
    fun `getSettings emits failure when settings is null`() = runTest {
        // Arrange
        val profile = UserProfileEntity("1", "John", "url", 5)
        every { localDataSource.observeProfile() } returns flowOf(profile)
        every { localDataSource.observeSettings() } returns flowOf(null)

        // Act
        repository.getSettings().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.NotFound)
            awaitComplete()
        }
    }
}
