package dev.esteki.ibank.core.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.data.local.dao.AccountDao
import dev.esteki.ibank.core.data.local.dao.MessageDao
import dev.esteki.ibank.core.data.local.dao.QuickActionDao
import dev.esteki.ibank.core.data.local.dao.SettingsDao
import dev.esteki.ibank.core.data.local.dao.TransactionDao
import dev.esteki.ibank.core.data.local.dao.UserProfileDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): IBankDatabase {
        return Room.databaseBuilder(context, IBankDatabase::class.java, "ibank.db")
            .addCallback(SeedDatabaseCallback())
            .build()
    }

    @Provides
    fun provideUserProfileDao(db: IBankDatabase): UserProfileDao = db.userProfileDao()

    @Provides
    fun provideAccountDao(db: IBankDatabase): AccountDao = db.accountDao()

    @Provides
    fun provideTransactionDao(db: IBankDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideQuickActionDao(db: IBankDatabase): QuickActionDao = db.quickActionDao()

    @Provides
    fun provideMessageDao(db: IBankDatabase): MessageDao = db.messageDao()

    @Provides
    fun provideSettingsDao(db: IBankDatabase): SettingsDao = db.settingsDao()
}
