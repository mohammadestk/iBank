package dev.esteki.ibank.core.domain.common

sealed interface AppError {
    data object Network : AppError
    data object Timeout : AppError
    data object Unauthorized : AppError
    data object NotFound : AppError
    data class Unknown(val message: String? = null) : AppError
}

fun Throwable.toAppError(): AppError = when (this) {
    is java.net.UnknownHostException,
    is java.net.ConnectException -> AppError.Network
    is java.util.concurrent.TimeoutException -> AppError.Timeout
    else -> AppError.Unknown(message)
}

fun AppError.toUserMessage(): String = when (this) {
    is AppError.Network -> "No internet connection. Please check your network."
    is AppError.Timeout -> "Request timed out. Please try again."
    is AppError.Unauthorized -> "Session expired. Please log in again."
    is AppError.NotFound -> "The requested resource was not found."
    is AppError.Unknown -> message ?: "An unexpected error occurred."
}
