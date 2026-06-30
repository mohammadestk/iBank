package dev.esteki.ibank.core.domain.home

import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.QuickAction
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.UserProfile
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
    suspend operator fun invoke(): HomeData {
        var profile: UserProfile? = null
        var accounts: List<Account> = emptyList()
        var quickActions: List<QuickAction> = emptyList()
        var transactions: List<Transaction> = emptyList()

        repository.getUserProfile().collect { profile = it }
        repository.getAccounts().collect { accounts = it }
        repository.getQuickActions().collect { quickActions = it }
        repository.getTransactions().collect { transactions = it }

        return HomeData(
            profile = profile!!,
            accounts = accounts,
            quickActions = quickActions,
            transactions = transactions,
        )
    }
}
