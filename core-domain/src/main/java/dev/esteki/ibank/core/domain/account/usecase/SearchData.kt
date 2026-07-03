package dev.esteki.ibank.core.domain.account.usecase

import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.transaction.model.Transaction

data class SearchData(
    val accounts: List<Account>,
    val transactions: List<Transaction>,
)
