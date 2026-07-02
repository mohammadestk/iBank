package dev.esteki.ibank.core.data.db.seed

import dev.esteki.ibank.core.data.account.AccountEntity
import dev.esteki.ibank.core.data.message.MessageEntity
import dev.esteki.ibank.core.data.quickaction.QuickActionEntity
import dev.esteki.ibank.core.data.settings.SettingsEntity
import dev.esteki.ibank.core.data.settings.SettingsItemDto
import dev.esteki.ibank.core.data.settings.SettingsSectionDto
import dev.esteki.ibank.core.data.transaction.TransactionEntity
import dev.esteki.ibank.core.data.user.UserProfileEntity
import dev.esteki.ibank.core.presentation.R
import kotlinx.serialization.json.Json
import java.util.UUID

object SeedData {

    private val json = Json { ignoreUnknownKeys = true }

    val userProfile = UserProfileEntity(
        id = "1",
        name = "Mohammad",
        avatarUrl = "",
        notificationCount = 5,
    )

    val accounts = listOf(
        AccountEntity(
            id = "1",
            name = "Main Account",
            balance = 24318.52,
            currency = "USD",
            accountNumber = "****1234",
            accountType = "SAVINGS",
        ),
    )

    val quickActions = listOf(
        QuickActionEntity(UUID.randomUUID().toString(), "Send", R.drawable.ic_send, "SEND"),
        QuickActionEntity(UUID.randomUUID().toString(), "Request", R.drawable.ic_request, "RECEIVE"),
        QuickActionEntity(UUID.randomUUID().toString(), "Top up", R.drawable.ic_topup, "MOBILE_RECHARGE"),
        QuickActionEntity(UUID.randomUUID().toString(), "Pay bill", R.drawable.ic_paybill, "PAY_BILLS"),
    )

    val transactions = listOf(
        TransactionEntity(UUID.randomUUID().toString(), "Spotify", "Subscription · Today", -10.99, false, "MUSIC", "Today"),
        TransactionEntity(UUID.randomUUID().toString(), "Salary", "Deposit · Yesterday", 4200.00, true, "SALARY", "Yesterday"),
        TransactionEntity(UUID.randomUUID().toString(), "Whole Foods", "Groceries · Oct 28", -86.24, false, "GROCERY", "Oct 28"),
        TransactionEntity(UUID.randomUUID().toString(), "To Maya R.", "Transfer · Oct 27", -120.00, false, "TRANSFER", "Oct 27"),
    )

    val messages = listOf(
        MessageEntity(
            id = UUID.randomUUID().toString(),
            title = "Payment Received",
            description = "You received $4,200.00 from Acme Corp",
            timestamp = "2 min ago",
            type = "TRANSACTION",
            isRead = false,
        ),
        MessageEntity(
            id = UUID.randomUUID().toString(),
            title = "Security Alert",
            description = "New login detected from iPhone 15 Pro",
            timestamp = "1 hour ago",
            type = "SECURITY",
            isRead = false,
        ),
        MessageEntity(
            id = UUID.randomUUID().toString(),
            title = "Bill Payment Due",
            description = "Electric bill of $142.50 is due in 3 days",
            timestamp = "3 hours ago",
            type = "SYSTEM",
            isRead = true,
        ),
        MessageEntity(
            id = UUID.randomUUID().toString(),
            title = "Special Offer",
            description = "Get 5% cashback on all international transactions",
            timestamp = "Yesterday",
            type = "PROMOTION",
            isRead = true,
        ),
        MessageEntity(
            id = UUID.randomUUID().toString(),
            title = "Transfer Complete",
            description = "$500.00 transferred to savings account",
            timestamp = "2 days ago",
            type = "TRANSACTION",
            isRead = true,
        ),
    )

    val settings: SettingsEntity
        get() {
            val sections = listOf(
                SettingsSectionDto(
                    id = "security",
                    title = "Security",
                    items = listOf(
                        SettingsItemDto("biometric", "Biometric login", R.drawable.ic_send, "TOGGLE", subtitle = "Face ID enabled", isChecked = true),
                        SettingsItemDto("2fa", "Two-factor authentication", R.drawable.ic_transfer, "NAVIGATION"),
                        SettingsItemDto("pin", "Change PIN", R.drawable.ic_request, "NAVIGATION"),
                    ),
                ),
                SettingsSectionDto(
                    id = "preferences",
                    title = "Preferences",
                    items = listOf(
                        SettingsItemDto("notifications", "Notifications", R.drawable.ic_paybill, "NAVIGATION"),
                        SettingsItemDto("language", "Language", R.drawable.ic_topup, "NAVIGATION", subtitle = "English (US)"),
                    ),
                ),
            )
            return SettingsEntity(
                id = "default",
                profileId = "1",
                sectionsJson = json.encodeToString(sections),
            )
        }
}
