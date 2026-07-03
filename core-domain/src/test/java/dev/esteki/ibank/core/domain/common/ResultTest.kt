package dev.esteki.ibank.core.domain.common

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ResultTest {

    @Test
    fun `onSuccess executes action when result is Success`() {
        // Arrange
        var executed = false
        val result = Result.Success(42)

        // Act
        result.onSuccess { executed = true }

        // Assert
        assertThat(executed).isTrue()
    }

    @Test
    fun `onSuccess does not execute action when result is Failure`() {
        // Arrange
        var executed = false
        val result = Result.Failure(AppError.Network)

        // Act
        result.onSuccess { executed = true }

        // Assert
        assertThat(executed).isFalse()
    }

    @Test
    fun `onFailure executes action when result is Failure`() {
        // Arrange
        var executedError: AppError? = null
        val error = AppError.Timeout
        val result = Result.Failure(error)

        // Act
        result.onFailure { executedError = it }

        // Assert
        assertThat(executedError).isEqualTo(error)
    }

    @Test
    fun `onFailure does not execute action when result is Success`() {
        // Arrange
        var executed = false
        val result = Result.Success("data")

        // Act
        result.onFailure { executed = true }

        // Assert
        assertThat(executed).isFalse()
    }

    @Test
    fun `onSuccess returns the same result`() {
        // Arrange
        val result = Result.Success(10)

        // Act
        val returned = result.onSuccess { }

        // Assert
        assertThat(returned).isEqualTo(result)
    }

    @Test
    fun `onFailure returns the same result`() {
        // Arrange
        val result = Result.Failure(AppError.NotFound)

        // Act
        val returned = result.onFailure { }

        // Assert
        assertThat(returned).isEqualTo(result)
    }

    @Test
    fun `map transforms Success data`() {
        // Arrange
        val result = Result.Success(5)

        // Act
        val mapped = result.map { it * 2 }

        // Assert
        assertThat(mapped).isEqualTo(Result.Success(10))
    }

    @Test
    fun `map returns same Failure when result is Failure`() {
        // Arrange
        val error = AppError.Network
        val result = Result.Failure(error)

        // Act
        val mapped = result.map { "should not happen" }

        // Assert
        assertThat(mapped).isEqualTo(Result.Failure(error))
    }

    @Test
    fun `safeCall returns Success when block succeeds`() = runTest {
        // Act
        val result = safeCall { 42 }

        // Assert
        assertThat(result).isEqualTo(Result.Success(42))
    }

    @Test
    fun `safeCall returns Failure when block throws`() = runTest {
        // Act
        val result = safeCall<Int> { throw java.net.UnknownHostException() }

        // Assert
        assertThat(result).isInstanceOf(Result.Failure::class.java)
        assertThat((result as Result.Failure).error).isEqualTo(AppError.Network)
    }
}
