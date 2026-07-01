package dev.esteki.ibank.core.data.search

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.AccountType
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.TransactionIcon
import dev.esteki.ibank.core.domain.search.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class FakeSearchRepository @Inject constructor() : SearchRepository {

    private val allAccounts = listOf(
        Account("1", "Main Account", 24318.52, "USD", "****1234", AccountType.SAVINGS),
        Account("2", "Checking Account", 5620.00, "USD", "****5678", AccountType.CHECKING),
        Account("3", "Credit Card", -1250.75, "USD", "****9012", AccountType.CREDIT),
    )

    private val allTransactions = listOf(
        Transaction(UUID.randomUUID().toString(), "Spotify", "Subscription", -10.99, false, TransactionIcon.MUSIC, "Today"),
        Transaction(UUID.randomUUID().toString(), "Salary", "Deposit", 4200.00, true, TransactionIcon.SALARY, "Yesterday"),
        Transaction(UUID.randomUUID().toString(), "Whole Foods", "Groceries", -86.24, false, TransactionIcon.GROCERY, "Oct 28"),
        Transaction(UUID.randomUUID().toString(), "To Maya R.", "Transfer", -120.00, false, TransactionIcon.TRANSFER, "Oct 27"),
    )

    override fun searchAccounts(query: String): Flow<Result<List<Account>>> = flowOf(
        if (query.isBlank()) {
            Result.Success(allAccounts)
        } else {
            Result.Success(
                allAccounts.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.accountNumber.contains(query, ignoreCase = true)
                }
            )
        }
    )

    override fun searchTransactions(query: String): Flow<Result<List<Transaction>>> = flowOf(
        if (query.isBlank()) {
            Result.Success(allTransactions)
        } else {
            Result.Success(
                allTransactions.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true)
                }
            )
        }
    )
}
