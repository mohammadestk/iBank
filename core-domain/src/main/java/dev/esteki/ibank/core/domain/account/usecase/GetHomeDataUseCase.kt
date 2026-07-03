package dev.esteki.ibank.core.domain.account.usecase

import dev.esteki.ibank.core.domain.account.repository.AccountRepository
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.quickaction.repository.QuickActionRepository
import dev.esteki.ibank.core.domain.transaction.repository.TransactionRepository
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
