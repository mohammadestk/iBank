package dev.esteki.ibank.core.domain.home

import dev.esteki.ibank.core.domain.common.AppError
import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.QuickAction
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class HomeData(
    val profile: UserProfile,
    val accounts: List<Account>,
    val quickActions: List<QuickAction>,
    val transactions: List<Transaction>,
)

class GetHomeDataUseCase @Inject constructor(
    private val repository: HomeRepository,
) {
    operator fun invoke(): Flow<Result<HomeData>> = combine(
        repository.getUserProfile(),
        repository.getAccounts(),
        repository.getQuickActions(),
        repository.getTransactions(),
    ) { profile, accounts, quickActions, transactions ->
        // Check if any result is a failure
        val failures = listOf(profile, accounts, quickActions, transactions)
            .filterIsInstance<Result.Failure>()
        
        if (failures.isNotEmpty()) {
            // Return the first failure
            return@combine Result.Failure(failures.first().error)
        }

        // All results are success, combine data
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
