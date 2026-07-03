package dev.esteki.ibank.feature.search

import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.transaction.model.Transaction

sealed interface SearchResult {
    data object Idle : SearchResult
    data object Loading : SearchResult
    data object Empty : SearchResult
    data class Success(val accounts: List<Account>, val transactions: List<Transaction>) : SearchResult
    data class Failure(val error: AppError, val message: String) : SearchResult
}
