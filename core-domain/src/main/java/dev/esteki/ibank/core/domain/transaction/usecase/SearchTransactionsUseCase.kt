package dev.esteki.ibank.core.domain.transaction.usecase

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    operator fun invoke(query: String): Flow<Result<List<Transaction>>> = repository.searchTransactions(query)
}
