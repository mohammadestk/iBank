package dev.esteki.ibank.core.data.account.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: String,
    val name: String,
    val balance: Double,
    val currency: String,
    val accountNumber: String,
    val accountType: String,
)
