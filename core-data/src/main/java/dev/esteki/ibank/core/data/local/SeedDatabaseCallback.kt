package dev.esteki.ibank.core.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.esteki.ibank.core.data.local.seed.SeedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeedDatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            db.execSQL("INSERT INTO user_profiles (id, name, avatarUrl, notificationCount) VALUES ('1', 'Mohammad', '', 5)")

            db.execSQL("INSERT INTO accounts (id, name, balance, currency, accountNumber, accountType) VALUES ('1', 'Main Account', 24318.52, 'USD', '****1234', 'SAVINGS')")

            SeedData.quickActions.forEach { entity ->
                db.execSQL(
                    "INSERT INTO quick_actions (id, label, iconRes, type) VALUES (?, ?, ?, ?)",
                    arrayOf<Any>(entity.id, entity.label, entity.iconRes, entity.type),
                )
            }

            SeedData.transactions.forEach { entity ->
                db.execSQL(
                    "INSERT INTO transactions (id, name, description, amount, isPositive, icon, date) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    arrayOf<Any>(entity.id, entity.name, entity.description, entity.amount, if (entity.isPositive) 1 else 0, entity.icon, entity.date),
                )
            }

            SeedData.messages.forEach { entity ->
                db.execSQL(
                    "INSERT INTO messages (id, title, description, timestamp, type, isRead) VALUES (?, ?, ?, ?, ?, ?)",
                    arrayOf<Any>(entity.id, entity.title, entity.description, entity.timestamp, entity.type, if (entity.isRead) 1 else 0),
                )
            }

            val settings = SeedData.settings
            db.execSQL(
                "INSERT INTO settings (id, profileId, sectionsJson) VALUES (?, ?, ?)",
                arrayOf<Any>(settings.id, settings.profileId, settings.sectionsJson),
            )
        }
    }
}
