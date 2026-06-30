package dev.esteki.ibank.core.domain.home

import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.QuickAction
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getUserProfile(): Flow<UserProfile>
    fun getAccounts(): Flow<List<Account>>
    fun getQuickActions(): Flow<List<QuickAction>>
    fun getTransactions(): Flow<List<Transaction>>
    fun getNotificationCount(): Flow<Int>
}
