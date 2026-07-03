package dev.esteki.ibank.core.domain.quickaction.model

data class QuickAction(
    val id: String,
    val label: String,
    val iconRes: Int,
    val type: QuickActionType,
)
