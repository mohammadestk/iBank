package dev.esteki.ibank.core.data.home

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeHomeRepository @Inject constructor() : HomeRepository {

    private val notificationCount = MutableStateFlow(5)

    override fun getUserProfile(): Flow<UserProfile> = flowOf(
        UserProfile(
            id = "1",
            name = "Aram",
            avatarUrl = "",
            notificationCount = 5,
        )
    )

    override fun getAccounts(): Flow<List<Account>> = flowOf(
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

    override fun getQuickActions(): Flow<List<QuickAction>> = flowOf(
        listOf(
            QuickAction("1", "Send", R.drawable.ic_send, QuickActionType.SEND),
            QuickAction("2", "Request", R.drawable.ic_request, QuickActionType.RECEIVE),
            QuickAction("3", "Top up", R.drawable.ic_topup, QuickActionType.MOBILE_RECHARGE),
            QuickAction("4", "Pay bill", R.drawable.ic_paybill, QuickActionType.PAY_BILLS),
        )
    )

    override fun getTransactions(): Flow<List<Transaction>> = flowOf(
        listOf(
            Transaction("1", "Spotify", "Subscription · Today", -10.99, false, TransactionIcon.MUSIC, "Today"),
            Transaction("2", "Salary", "Deposit · Yesterday", 4200.00, true, TransactionIcon.SALARY, "Yesterday"),
            Transaction("3", "Whole Foods", "Groceries · Oct 28", -86.24, false, TransactionIcon.GROCERY, "Oct 28"),
            Transaction("4", "To Maya R.", "Transfer · Oct 27", -120.00, false, TransactionIcon.TRANSFER, "Oct 27"),
        )
    )

    override fun getNotificationCount(): Flow<Int> = notificationCount
}
