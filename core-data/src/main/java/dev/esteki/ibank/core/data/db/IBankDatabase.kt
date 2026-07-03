package dev.esteki.ibank.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.esteki.ibank.core.data.account.dao.AccountDao
import dev.esteki.ibank.core.data.account.entity.AccountEntity
import dev.esteki.ibank.core.data.message.dao.MessageDao
import dev.esteki.ibank.core.data.message.entity.MessageEntity
import dev.esteki.ibank.core.data.quickaction.dao.QuickActionDao
import dev.esteki.ibank.core.data.quickaction.entity.QuickActionEntity
import dev.esteki.ibank.core.data.settings.dao.SettingsDao
import dev.esteki.ibank.core.data.settings.entity.SettingsEntity
import dev.esteki.ibank.core.data.transaction.dao.TransactionDao
import dev.esteki.ibank.core.data.transaction.entity.TransactionEntity
import dev.esteki.ibank.core.data.user.dao.UserProfileDao
import dev.esteki.ibank.core.data.user.entity.UserProfileEntity

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
