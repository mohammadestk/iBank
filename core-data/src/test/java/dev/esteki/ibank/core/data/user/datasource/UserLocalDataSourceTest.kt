package dev.esteki.ibank.core.data.user.datasource

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.user.dao.UserProfileDao
import dev.esteki.ibank.core.data.user.entity.UserProfileEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserLocalDataSourceTest {

    private lateinit var dao: UserProfileDao
    private lateinit var dataSource: UserLocalDataSource

    @Before
    fun setup() {
        dao = mockk()
        dataSource = UserLocalDataSource(dao)
    }

    @Test
    fun `observeProfile delegates to dao observeProfile`() = runTest {
        // Arrange
        val entity = UserProfileEntity("1", "John", "url", 5)
        every { dao.observeProfile() } returns flowOf(entity)

        // Act
        dataSource.observeProfile().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isNotNull()
            assertThat(result!!.name).isEqualTo("John")
            verify { dao.observeProfile() }
            awaitComplete()
        }
    }

    @Test
    fun `observeProfile returns null when no profile exists`() = runTest {
        every { dao.observeProfile() } returns flowOf(null)

        dataSource.observeProfile().test {
            assertThat(awaitItem()).isNull()
            awaitComplete()
        }
    }
}
