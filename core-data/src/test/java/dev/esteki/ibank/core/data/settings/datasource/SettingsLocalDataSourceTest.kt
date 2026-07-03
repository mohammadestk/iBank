package dev.esteki.ibank.core.data.settings.datasource

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.settings.dao.SettingsDao
import dev.esteki.ibank.core.data.settings.entity.SettingsEntity
import dev.esteki.ibank.core.data.user.dao.UserProfileDao
import dev.esteki.ibank.core.data.user.entity.UserProfileEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SettingsLocalDataSourceTest {

    private lateinit var settingsDao: SettingsDao
    private lateinit var userProfileDao: UserProfileDao
    private lateinit var dataSource: SettingsLocalDataSource

    @Before
    fun setup() {
        settingsDao = mockk()
        userProfileDao = mockk()
        dataSource = SettingsLocalDataSource(settingsDao, userProfileDao)
    }

    @Test
    fun `observeSettings delegates to settingsDao observe`() = runTest {
        // Arrange
        val entity = SettingsEntity("default", "1", "{}")
        every { settingsDao.observe() } returns flowOf(entity)

        // Act
        dataSource.observeSettings().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isNotNull()
            assertThat(result!!.id).isEqualTo("default")
            verify { settingsDao.observe() }
            awaitComplete()
        }
    }

    @Test
    fun `observeSettings returns null when no settings exist`() = runTest {
        every { settingsDao.observe() } returns flowOf(null)

        dataSource.observeSettings().test {
            assertThat(awaitItem()).isNull()
            awaitComplete()
        }
    }

    @Test
    fun `observeProfile delegates to userProfileDao observeProfile`() = runTest {
        // Arrange
        val entity = UserProfileEntity("1", "John", "url", 5)
        every { userProfileDao.observeProfile() } returns flowOf(entity)

        // Act
        dataSource.observeProfile().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isNotNull()
            assertThat(result!!.name).isEqualTo("John")
            verify { userProfileDao.observeProfile() }
            awaitComplete()
        }
    }

    @Test
    fun `observeProfile returns null when no profile exists`() = runTest {
        every { userProfileDao.observeProfile() } returns flowOf(null)

        dataSource.observeProfile().test {
            assertThat(awaitItem()).isNull()
            awaitComplete()
        }
    }
}
