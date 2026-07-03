package dev.esteki.ibank.core.domain.usecase

import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.account.repository.AccountRepository
import dev.esteki.ibank.core.domain.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    operator fun invoke(): Flow<Result<List<Account>>> = repository.getAccounts()
}
