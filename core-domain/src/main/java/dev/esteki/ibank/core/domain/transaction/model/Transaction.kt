package dev.esteki.ibank.core.domain.transaction.model

data class Transaction(
    val id: String,
    val name: String,
    val description: String,
    val amount: Double,
    val isPositive: Boolean,
    val icon: TransactionIcon,
    val date: String,
)
