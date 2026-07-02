package dev.esteki.ibank.core.domain.search

import dev.esteki.ibank.core.domain.account.AccountRepository
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.account.Account
import dev.esteki.ibank.core.domain.transaction.Transaction
import dev.esteki.ibank.core.domain.transaction.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class SearchData(
    val accounts: List<Account>,
    val transactions: List<Transaction>,
)

class SearchUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {
    operator fun invoke(query: String): Flow<Result<SearchData>> = combine(
        accountRepository.searchAccounts(query),
        transactionRepository.searchTransactions(query),
    ) { accounts, transactions ->
        val failures = listOf(accounts, transactions)
            .filterIsInstance<Result.Failure>()

        if (failures.isNotEmpty()) {
            return@combine Result.Failure(failures.first().error)
        }

        val accountsData = (accounts as Result.Success).data
        val transactionsData = (transactions as Result.Success).data

        Result.Success(
            SearchData(
                accounts = accountsData,
                transactions = transactionsData,
            )
        )
    }
}
