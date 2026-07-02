package dev.esteki.ibank.core.data.db.mapper

import dev.esteki.ibank.core.data.account.AccountEntity
import dev.esteki.ibank.core.data.message.MessageEntity
import dev.esteki.ibank.core.data.quickaction.QuickActionEntity
import dev.esteki.ibank.core.data.transaction.TransactionEntity
import dev.esteki.ibank.core.data.user.UserProfileEntity
import dev.esteki.ibank.core.domain.account.Account
import dev.esteki.ibank.core.domain.account.AccountType
import dev.esteki.ibank.core.domain.message.Message
import dev.esteki.ibank.core.domain.message.MessageType
import dev.esteki.ibank.core.domain.quickaction.QuickAction
import dev.esteki.ibank.core.domain.quickaction.QuickActionType
import dev.esteki.ibank.core.domain.transaction.Transaction
import dev.esteki.ibank.core.domain.transaction.TransactionIcon
import dev.esteki.ibank.core.domain.user.UserProfile

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
