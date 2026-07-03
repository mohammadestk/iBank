package dev.esteki.ibank.core.domain.usecase

import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.repository.AccountRepository
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.quickaction.repository.QuickActionRepository
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.repository.TransactionRepository
import dev.esteki.ibank.core.domain.user.model.UserProfile
import dev.esteki.ibank.core.domain.user.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetHomeDataUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val userProfileRepository: UserProfileRepository,
    private val quickActionRepository: QuickActionRepository,
) {
    operator fun invoke(): Flow<Result<HomeData>> = combine(
        userProfileRepository.getUserProfile(),
        accountRepository.getAccounts(),
        quickActionRepository.getQuickActions(),
        transactionRepository.getTransactions(),
    ) { profile, accounts, quickActions, transactions ->
        val failures = listOf(profile, accounts, quickActions, transactions)
            .filterIsInstance<Result.Failure>()

        if (failures.isNotEmpty()) {
            return@combine Result.Failure(failures.first().error)
        }

        val profileData = (profile as Result.Success).data
        val accountsData = (accounts as Result.Success).data
        val quickActionsData = (quickActions as Result.Success).data
        val transactionsData = (transactions as Result.Success).data

        Result.Success(
            HomeData(
                profile = profileData,
                accounts = accountsData,
                quickActions = quickActionsData,
                transactions = transactionsData,
            )
        )
    }
}

data class HomeData(
    val profile: UserProfile,
    val accounts: List<Account>,
    val quickActions: List<QuickAction>,
    val transactions: List<Transaction>,
)
