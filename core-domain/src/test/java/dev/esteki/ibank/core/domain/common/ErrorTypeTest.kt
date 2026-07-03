package dev.esteki.ibank.core.domain.common

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ErrorTypeTest {

    @Test
    fun `UnknownHostException maps to Network error`() {
        val error = java.net.UnknownHostException().toAppError()
        assertThat(error).isEqualTo(AppError.Network)
    }

    @Test
    fun `ConnectException maps to Network error`() {
        val error = java.net.ConnectException().toAppError()
        assertThat(error).isEqualTo(AppError.Network)
    }

    @Test
    fun `TimeoutException maps to Timeout error`() {
        val error = java.util.concurrent.TimeoutException().toAppError()
        assertThat(error).isEqualTo(AppError.Timeout)
    }

    @Test
    fun `generic exception maps to Unknown error with message`() {
        val error = RuntimeException("boom").toAppError()
        assertThat(error).isInstanceOf(AppError.Unknown::class.java)
        assertThat((error as AppError.Unknown).message).isEqualTo("boom")
    }

    @Test
    fun `Network error produces correct user message`() {
        assertThat(AppError.Network.toUserMessage()).isEqualTo("No internet connection. Please check your network.")
    }

    @Test
    fun `Timeout error produces correct user message`() {
        assertThat(AppError.Timeout.toUserMessage()).isEqualTo("Request timed out. Please try again.")
    }

    @Test
    fun `Unauthorized error produces correct user message`() {
        assertThat(AppError.Unauthorized.toUserMessage()).isEqualTo("Session expired. Please log in again.")
    }

    @Test
    fun `NotFound error produces correct user message`() {
        assertThat(AppError.NotFound.toUserMessage()).isEqualTo("The requested resource was not found.")
    }

    @Test
    fun `Unknown error with message produces that message`() {
        assertThat(AppError.Unknown("custom").toUserMessage()).isEqualTo("custom")
    }

    @Test
    fun `Unknown error without message produces default message`() {
        assertThat(AppError.Unknown().toUserMessage()).isEqualTo("An unexpected error occurred.")
    }
}
