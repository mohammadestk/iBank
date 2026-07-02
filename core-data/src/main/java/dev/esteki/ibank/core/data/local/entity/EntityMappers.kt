package dev.esteki.ibank.core.data.local.entity

import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.AccountType
import dev.esteki.ibank.core.domain.model.Message
import dev.esteki.ibank.core.domain.model.MessageType
import dev.esteki.ibank.core.domain.model.QuickAction
import dev.esteki.ibank.core.domain.model.QuickActionType
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.TransactionIcon
import dev.esteki.ibank.core.domain.model.UserProfile

fun UserProfileEntity.toDomain() = UserProfile(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    notificationCount = notificationCount,
)

fun UserProfile.toEntity() = UserProfileEntity(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    notificationCount = notificationCount,
)

fun AccountEntity.toDomain() = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
    accountNumber = accountNumber,
    accountType = AccountType.valueOf(accountType),
)

fun Account.toEntity() = AccountEntity(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
    accountNumber = accountNumber,
    accountType = accountType.name,
)

fun TransactionEntity.toDomain() = Transaction(
    id = id,
    name = name,
    description = description,
    amount = amount,
    isPositive = isPositive,
    icon = TransactionIcon.valueOf(icon),
    date = date,
)

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    name = name,
    description = description,
    amount = amount,
    isPositive = isPositive,
    icon = icon.name,
    date = date,
)

fun QuickActionEntity.toDomain() = QuickAction(
    id = id,
    label = label,
    iconRes = iconRes,
    type = QuickActionType.valueOf(type),
)

fun QuickAction.toEntity() = QuickActionEntity(
    id = id,
    label = label,
    iconRes = iconRes,
    type = type.name,
)

fun MessageEntity.toDomain() = Message(
    id = id,
    title = title,
    description = description,
    timestamp = timestamp,
    type = MessageType.valueOf(type),
    isRead = isRead,
)

fun Message.toEntity() = MessageEntity(
    id = id,
    title = title,
    description = description,
    timestamp = timestamp,
    type = type.name,
    isRead = isRead,
)
