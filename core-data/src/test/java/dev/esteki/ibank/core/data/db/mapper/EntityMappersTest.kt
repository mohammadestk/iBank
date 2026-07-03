package dev.esteki.ibank.core.data.db.mapper

import com.google.common.truth.Truth.assertThat
import dev.esteki.ibank.core.data.account.entity.AccountEntity
import dev.esteki.ibank.core.data.message.entity.MessageEntity
import dev.esteki.ibank.core.data.quickaction.entity.QuickActionEntity
import dev.esteki.ibank.core.data.transaction.entity.TransactionEntity
import dev.esteki.ibank.core.data.user.entity.UserProfileEntity
import dev.esteki.ibank.core.domain.account.model.AccountType
import dev.esteki.ibank.core.domain.message.model.MessageType
import dev.esteki.ibank.core.domain.quickaction.model.QuickActionType
import dev.esteki.ibank.core.domain.transaction.model.TransactionIcon
import org.junit.Test

class EntityMappersTest {

    // UserProfile

    @Test
    fun `UserProfileEntity toDomain maps all fields`() {
        // Arrange
        val entity = UserProfileEntity("1", "John", "url", 5)

        // Act
        val domain = entity.toDomain()

        // Assert
        assertThat(domain.id).isEqualTo("1")
        assertThat(domain.name).isEqualTo("John")
        assertThat(domain.avatarUrl).isEqualTo("url")
        assertThat(domain.notificationCount).isEqualTo(5)
    }

    @Test
    fun `UserProfile toEntity maps all fields`() {
        // Arrange
        val domain = dev.esteki.ibank.core.domain.user.model.UserProfile("1", "John", "url", 5)

        // Act
        val entity = domain.toEntity()

        // Assert
        assertThat(entity.id).isEqualTo("1")
        assertThat(entity.name).isEqualTo("John")
        assertThat(entity.avatarUrl).isEqualTo("url")
        assertThat(entity.notificationCount).isEqualTo(5)
    }

    // Account

    @Test
    fun `AccountEntity toDomain maps all fields and converts enum`() {
        // Arrange
        val entity = AccountEntity("1", "Main", 1000.0, "USD", "****1234", "SAVINGS")

        // Act
        val domain = entity.toDomain()

        // Assert
        assertThat(domain.id).isEqualTo("1")
        assertThat(domain.name).isEqualTo("Main")
        assertThat(domain.balance).isEqualTo(1000.0)
        assertThat(domain.currency).isEqualTo("USD")
        assertThat(domain.accountNumber).isEqualTo("****1234")
        assertThat(domain.accountType).isEqualTo(AccountType.SAVINGS)
    }

    @Test
    fun `Account toEntity maps all fields and converts enum`() {
        // Arrange
        val domain = dev.esteki.ibank.core.domain.account.model.Account("1", "Main", 1000.0, "USD", "****1234", AccountType.CHECKING)

        // Act
        val entity = domain.toEntity()

        // Assert
        assertThat(entity.accountType).isEqualTo("CHECKING")
    }

    // Transaction

    @Test
    fun `TransactionEntity toDomain maps all fields and converts enum`() {
        // Arrange
        val entity = TransactionEntity("1", "Spotify", "Sub", -10.99, false, "MUSIC", "Today")

        // Act
        val domain = entity.toDomain()

        // Assert
        assertThat(domain.id).isEqualTo("1")
        assertThat(domain.name).isEqualTo("Spotify")
        assertThat(domain.description).isEqualTo("Sub")
        assertThat(domain.amount).isEqualTo(-10.99)
        assertThat(domain.isPositive).isFalse()
        assertThat(domain.icon).isEqualTo(TransactionIcon.MUSIC)
        assertThat(domain.date).isEqualTo("Today")
    }

    @Test
    fun `Transaction toEntity maps all fields and converts enum`() {
        // Arrange
        val domain = dev.esteki.ibank.core.domain.transaction.model.Transaction("1", "Salary", "Deposit", 4200.0, true, TransactionIcon.SALARY, "Yesterday")

        // Act
        val entity = domain.toEntity()

        // Assert
        assertThat(entity.icon).isEqualTo("SALARY")
        assertThat(entity.isPositive).isTrue()
    }

    // QuickAction

    @Test
    fun `QuickActionEntity toDomain maps all fields and converts enum`() {
        // Arrange
        val entity = QuickActionEntity("1", "Send", 123, "SEND")

        // Act
        val domain = entity.toDomain()

        // Assert
        assertThat(domain.id).isEqualTo("1")
        assertThat(domain.label).isEqualTo("Send")
        assertThat(domain.iconRes).isEqualTo(123)
        assertThat(domain.type).isEqualTo(QuickActionType.SEND)
    }

    @Test
    fun `QuickAction toEntity maps all fields and converts enum`() {
        // Arrange
        val domain = dev.esteki.ibank.core.domain.quickaction.model.QuickAction("1", "Send", 123, QuickActionType.RECEIVE)

        // Act
        val entity = domain.toEntity()

        // Assert
        assertThat(entity.type).isEqualTo("RECEIVE")
    }

    // Message

    @Test
    fun `MessageEntity toDomain maps all fields and converts enum`() {
        // Arrange
        val entity = MessageEntity("1", "Title", "Desc", "2 min ago", "SECURITY", false)

        // Act
        val domain = entity.toDomain()

        // Assert
        assertThat(domain.id).isEqualTo("1")
        assertThat(domain.title).isEqualTo("Title")
        assertThat(domain.description).isEqualTo("Desc")
        assertThat(domain.timestamp).isEqualTo("2 min ago")
        assertThat(domain.type).isEqualTo(MessageType.SECURITY)
        assertThat(domain.isRead).isFalse()
    }

    @Test
    fun `Message toEntity maps all fields and converts enum`() {
        // Arrange
        val domain = dev.esteki.ibank.core.domain.message.model.Message("1", "Title", "Desc", "2 min ago", MessageType.PROMOTION, true)

        // Act
        val entity = domain.toEntity()

        // Assert
        assertThat(entity.type).isEqualTo("PROMOTION")
        assertThat(entity.isRead).isTrue()
    }
}
