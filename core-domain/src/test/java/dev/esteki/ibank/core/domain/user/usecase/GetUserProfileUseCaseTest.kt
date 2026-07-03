package dev.esteki.ibank.core.domain.user.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.user.model.UserProfile
import dev.esteki.ibank.core.domain.user.repository.UserProfileRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserProfileUseCaseTest {

    private lateinit var repository: UserProfileRepository
    private lateinit var useCase: GetUserProfileUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetUserProfileUseCase(repository)
    }

    @Test
    fun `invoke returns profile from repository`() = runTest {
        // Arrange
        val profile = UserProfile("1", "John", "url", 5)
        every { repository.getUserProfile() } returns flowOf(Result.Success(profile))

        // Act & Assert
        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isEqualTo(profile)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        every { repository.getUserProfile() } returns flowOf(Result.Failure(AppError.NotFound))

        useCase().test {
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            assertThat((result as Result.Failure).error).isEqualTo(AppError.NotFound)
            awaitComplete()
        }
    }
}
