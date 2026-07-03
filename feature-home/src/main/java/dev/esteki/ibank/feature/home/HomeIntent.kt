package dev.esteki.ibank.feature.home

import dev.esteki.ibank.core.domain.quickaction.model.QuickAction

sealed interface HomeIntent {
    data object LoadData : HomeIntent
    data class QuickActionClicked(val action: QuickAction) : HomeIntent
    data object NotificationClicked : HomeIntent
    data object SeeAllTransactions : HomeIntent
}
