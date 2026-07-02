package dev.esteki.ibank.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.esteki.ibank.core.data.local.dao.AccountDao
import dev.esteki.ibank.core.data.local.dao.MessageDao
import dev.esteki.ibank.core.data.local.dao.QuickActionDao
import dev.esteki.ibank.core.data.local.dao.SettingsDao
import dev.esteki.ibank.core.data.local.dao.TransactionDao
import dev.esteki.ibank.core.data.local.dao.UserProfileDao
import dev.esteki.ibank.core.data.local.entity.AccountEntity
import dev.esteki.ibank.core.data.local.entity.MessageEntity
import dev.esteki.ibank.core.data.local.entity.QuickActionEntity
import dev.esteki.ibank.core.data.local.entity.SettingsEntity
import dev.esteki.ibank.core.data.local.entity.TransactionEntity
import dev.esteki.ibank.core.data.local.entity.UserProfileEntity

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
