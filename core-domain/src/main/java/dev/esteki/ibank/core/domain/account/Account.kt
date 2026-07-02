package dev.esteki.ibank.core.domain.account

data class Account(
    val id: String,
    val name: String,
    val balance: Double,
    val currency: String,
    val accountNumber: String,
    val accountType: AccountType,
)

enum class AccountType {
    SAVINGS,
    CHECKING,
    CREDIT,
}
