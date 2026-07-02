package dev.esteki.ibank.core.data.user

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserProfileRepositoryImplTest {

    private lateinit var localDataSource: UserLocalDataSource
    private lateinit var repository: UserProfileRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mockk()
        repository = UserProfileRepositoryImpl(localDataSource)
    }

    @Test
    fun `getUserProfile emits success when entity exists`() = runTest {
        // Arrange
        val entity = UserProfileEntity("1", "John", "url", 5)
        every { localDataSource.observeProfile() } returns flowOf(entity)

        // Act
        repository.getUserProfile().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            val profile = (result as Result.Success).data
            assertThat(profile.name).isEqualTo("John")
            awaitComplete()
        }
    }

    @Test
    fun `getUserProfile emits failure when entity is null`() = runTest {
        // Arrange
        every { localDataSource.observeProfile() } returns flowOf(null)

        // Act
        repository.getUserProfile().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.NotFound)
            awaitComplete()
        }
    }

    @Test
    fun `getNotificationCount emits count when entity exists`() = runTest {
        // Arrange
        val entity = UserProfileEntity("1", "John", "url", 5)
        every { localDataSource.observeProfile() } returns flowOf(entity)

        // Act
        repository.getNotificationCount().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(5)
            awaitComplete()
        }
    }

    @Test
    fun `getNotificationCount emits 0 when entity is null`() = runTest {
        // Arrange
        every { localDataSource.observeProfile() } returns flowOf(null)

        // Act
        repository.getNotificationCount().test {
            val result = awaitItem()

            // Assert
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(0)
            awaitComplete()
        }
    }
}
