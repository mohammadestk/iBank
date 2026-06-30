package dev.esteki.ibank.core.data.home

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.home.HomeRepository
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.AccountType
import dev.esteki.ibank.core.domain.model.QuickAction
import dev.esteki.ibank.core.domain.model.QuickActionType
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.TransactionIcon
import dev.esteki.ibank.core.domain.model.UserProfile
import dev.esteki.ibank.core.presentation.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class FakeHomeRepository @Inject constructor() : HomeRepository {

    override fun getUserProfile(): Flow<Result<UserProfile>> = flowOf(
        Result.Success(
            UserProfile(
                id = "1",
                name = "Aram",
                avatarUrl = "",
                notificationCount = 5,
            )
        )
    )

    override fun getAccounts(): Flow<Result<List<Account>>> = flowOf(
        Result.Success(
            listOf(
                Account(
                    id = "1",
                    name = "Main Account",
                    balance = 24318.52,
                    currency = "USD",
                    accountNumber = "****1234",
                    accountType = AccountType.SAVINGS,
                ),
            )
        )
    )

    override fun getQuickActions(): Flow<Result<List<QuickAction>>> = flowOf(
        Result.Success(
            listOf(
                QuickAction(UUID.randomUUID().toString(), "Send", R.drawable.ic_send, QuickActionType.SEND),
                QuickAction(UUID.randomUUID().toString(), "Request", R.drawable.ic_request, QuickActionType.RECEIVE),
                QuickAction(UUID.randomUUID().toString(), "Top up", R.drawable.ic_topup, QuickActionType.MOBILE_RECHARGE),
                QuickAction(UUID.randomUUID().toString(), "Pay bill", R.drawable.ic_paybill, QuickActionType.PAY_BILLS),
            )
        )
    )

    override fun getTransactions(): Flow<Result<List<Transaction>>> = flowOf(
        Result.Success(
            listOf(
                Transaction(UUID.randomUUID().toString(), "Spotify", "Subscription · Today", -10.99, false, TransactionIcon.MUSIC, "Today"),
                Transaction(UUID.randomUUID().toString(), "Salary", "Deposit · Yesterday", 4200.00, true, TransactionIcon.SALARY, "Yesterday"),
                Transaction(UUID.randomUUID().toString(), "Whole Foods", "Groceries · Oct 28", -86.24, false, TransactionIcon.GROCERY, "Oct 28"),
                Transaction(UUID.randomUUID().toString(), "To Maya R.", "Transfer · Oct 27", -120.00, false, TransactionIcon.TRANSFER, "Oct 27"),
            )
        )
    )

    override fun getNotificationCount(): Flow<Result<Int>> = flowOf(
        Result.Success(5)
    )
}
