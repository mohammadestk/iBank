package dev.esteki.ibank.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.esteki.ibank.core.data.account.AccountDao
import dev.esteki.ibank.core.data.account.AccountEntity
import dev.esteki.ibank.core.data.message.MessageDao
import dev.esteki.ibank.core.data.message.MessageEntity
import dev.esteki.ibank.core.data.quickaction.QuickActionDao
import dev.esteki.ibank.core.data.quickaction.QuickActionEntity
import dev.esteki.ibank.core.data.settings.SettingsDao
import dev.esteki.ibank.core.data.settings.SettingsEntity
import dev.esteki.ibank.core.data.transaction.TransactionDao
import dev.esteki.ibank.core.data.transaction.TransactionEntity
import dev.esteki.ibank.core.data.user.UserProfileDao
import dev.esteki.ibank.core.data.user.UserProfileEntity

@Database(
    entities = [
        UserProfileEntity::class,
        AccountEntity::class,
        TransactionEntity::class,
        QuickActionEntity::class,
        MessageEntity::class,
        SettingsEntity::class,
    ],
    version = 1,
)
abstract class IBankDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun quickActionDao(): QuickActionDao
    abstract fun messageDao(): MessageDao
    abstract fun settingsDao(): SettingsDao
}
