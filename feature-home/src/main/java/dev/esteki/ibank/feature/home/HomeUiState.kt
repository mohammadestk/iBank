package dev.esteki.ibank.feature.home

import androidx.compose.runtime.Stable
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.transaction.model.Transaction

@Stable
data class HomeUiState(
    val result: HomeResult,
    val accounts: List<Account>,
    val quickActions: List<QuickAction>,
    val transactions: List<Transaction>,
)
