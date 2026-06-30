package dev.esteki.ibank.core.domain.model

data class QuickAction(
    val id: String,
    val label: String,
    val iconRes: Int,
    val type: QuickActionType,
)

enum class QuickActionType {
    SEND,
    RECEIVE,
    PAY_BILLS,
    MOBILE_RECHARGE,
    SAVINGS,
    LOAN,
    INSURANCE,
    INVESTMENTS,
    MORE,
}
