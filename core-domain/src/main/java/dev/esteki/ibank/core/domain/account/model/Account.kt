package dev.esteki.ibank.core.domain.account.model

data class Account(
    val id: String,
    val name: String,
    val balance: Double,
    val currency: String,
    val accountNumber: String,
    val accountType: AccountType,
)
