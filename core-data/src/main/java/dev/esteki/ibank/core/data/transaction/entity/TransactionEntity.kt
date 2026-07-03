package dev.esteki.ibank.core.data.transaction.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val amount: Double,
    val isPositive: Boolean,
    val icon: String,
    val date: String,
)
