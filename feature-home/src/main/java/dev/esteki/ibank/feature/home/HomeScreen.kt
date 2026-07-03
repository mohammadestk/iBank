package dev.esteki.ibank.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.esteki.ibank.core.domain.account.model.Account
import dev.esteki.ibank.core.domain.quickaction.model.QuickAction
import dev.esteki.ibank.core.domain.transaction.model.Transaction
import dev.esteki.ibank.core.domain.transaction.model.TransactionIcon
import java.text.NumberFormat
import java.util.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.result) {
        is HomeResult.Idle, is HomeResult.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is HomeResult.Success -> {
            HomeContent(
                modifier = modifier,
                uiState = uiState,
                result = uiState.result as HomeResult.Success,
                onQuickActionClick = { viewModel.onIntent(HomeIntent.QuickActionClicked(it)) },
                onNotificationClick = { viewModel.onIntent(HomeIntent.NotificationClicked) },
                onSeeAllClick = { viewModel.onIntent(HomeIntent.SeeAllTransactions) },
            )
        }

        is HomeResult.Failure -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = (uiState.result as HomeResult.Failure).message,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    result: HomeResult.Success,
    onQuickActionClick: (QuickAction) -> Unit,
    onNotificationClick: () -> Unit,
    onSeeAllClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        item {
            HomeTopBar(
                modifier = Modifier.fillMaxWidth(),
                userName = result.userName,
                notificationCount = result.notificationCount,
                onNotificationClick = onNotificationClick,
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(uiState.accounts, key = { it.id }) { account ->
            BalanceCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                account = account,
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            QuickActionsRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                actions = uiState.quickActions,
                onActionClick = onQuickActionClick,
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            SectionHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = "Recent activity",
                actionText = "See all",
                onActionClick = onSeeAllClick,
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(uiState.transactions, key = { it.id }) { transaction ->
            TransactionItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                transaction = transaction,
            )
        }
    }
}

@Composable
private fun BalanceCard(
    modifier: Modifier = Modifier,
    account: Account,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text(
                text = "TOTAL BALANCE",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    letterSpacing = 0.04.sp,
                ),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = formatCurrency(account.balance, account.currency),
                style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.16f))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "▲ 2.4% this month",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                )
            }
        }
    }
}

@Composable
private fun QuickActionsRow(
    modifier: Modifier = Modifier,
    actions: List<QuickAction>,
    onActionClick: (QuickAction) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        actions.forEach { action ->
            QuickActionItem(
                action = action,
                onClick = { onActionClick(action) },
            )
        }
    }
}

@Composable
private fun QuickActionItem(
    modifier: Modifier = Modifier,
    action: QuickAction,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(action.iconRes),
                contentDescription = action.label,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = action.label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    actionText: String,
    onActionClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = Modifier
                .clickable(onClick = onActionClick)
                .padding(2.dp),
            text = actionText,
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}

@Composable
private fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: Transaction,
) {
    Row(
        modifier = modifier
            .clickable { }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(getTransactionIconBackground(transaction.icon)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(getTransactionIcon(transaction.icon)),
                contentDescription = transaction.name,
                tint = getTransactionIconTint(transaction.icon),
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            Text(
                text = transaction.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
        Text(
            text = formatTransactionAmount(transaction.amount, transaction.isPositive),
            style = MaterialTheme.typography.labelLarge.copy(
                color = if (transaction.isPositive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            ),
        )
    }
}

@Composable
private fun getTransactionIconBackground(icon: dev.esteki.ibank.core.domain.transaction.model.TransactionIcon): androidx.compose.ui.graphics.Color {
    return when (icon) {
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.MUSIC -> MaterialTheme.colorScheme.tertiaryContainer
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.SALARY -> MaterialTheme.colorScheme.primaryContainer
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.GROCERY -> MaterialTheme.colorScheme.secondaryContainer
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.TRANSFER -> MaterialTheme.colorScheme.surfaceVariant
    }
}

@Composable
private fun getTransactionIconTint(icon: dev.esteki.ibank.core.domain.transaction.model.TransactionIcon): androidx.compose.ui.graphics.Color {
    return when (icon) {
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.MUSIC -> MaterialTheme.colorScheme.onTertiaryContainer
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.SALARY -> MaterialTheme.colorScheme.onPrimaryContainer
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.GROCERY -> MaterialTheme.colorScheme.onSecondaryContainer
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.TRANSFER -> MaterialTheme.colorScheme.onSurfaceVariant
    }
}

private fun getTransactionIcon(icon: dev.esteki.ibank.core.domain.transaction.model.TransactionIcon): Int {
    return when (icon) {
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.MUSIC -> dev.esteki.ibank.core.presentation.R.drawable.ic_music
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.SALARY -> dev.esteki.ibank.core.presentation.R.drawable.ic_salary
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.GROCERY -> dev.esteki.ibank.core.presentation.R.drawable.ic_grocery
        dev.esteki.ibank.core.domain.transaction.model.TransactionIcon.TRANSFER -> dev.esteki.ibank.core.presentation.R.drawable.ic_transfer
    }
}

private fun formatCurrency(amount: Double, currency: String): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    return format.format(amount)
}

private fun formatTransactionAmount(amount: Double, isPositive: Boolean): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    val prefix = if (isPositive) "+" else ""
    return "$prefix${format.format(amount)}"
}
