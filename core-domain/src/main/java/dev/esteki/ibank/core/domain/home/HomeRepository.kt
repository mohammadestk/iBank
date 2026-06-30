package dev.esteki.ibank.core.domain.home

import dev.esteki.ibank.core.domain.common.Result
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.QuickAction
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getUserProfile(): Flow<Result<UserProfile>>
    fun getAccounts(): Flow<Result<List<Account>>>
    fun getQuickActions(): Flow<Result<List<QuickAction>>>
    fun getTransactions(): Flow<Result<List<Transaction>>>
    fun getNotificationCount(): Flow<Result<Int>>
}
