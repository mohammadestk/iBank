package dev.esteki.ibank.feature.search

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.esteki.ibank.core.domain.model.Account
import dev.esteki.ibank.core.domain.model.AccountType
import dev.esteki.ibank.core.domain.model.Transaction
import dev.esteki.ibank.core.domain.model.TransactionIcon
import dev.esteki.ibank.core.presentation.R
import dev.esteki.ibank.core.presentation.theme.IBankTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        SearchTopBar()

        SearchBar(
            query = uiState.query,
            onQueryChange = { viewModel.onIntent(SearchIntent.QueryChanged(it)) },
            onClearClick = { viewModel.onIntent(SearchIntent.ClearSearch) },
        )

        Spacer(modifier = Modifier.height(12.dp))

        FilterChipRow(
            selectedFilter = uiState.selectedFilter,
            onFilterSelected = { viewModel.onIntent(SearchIntent.FilterSelected(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState.result) {
            is SearchResult.Idle -> {
                DefaultContent(
                    recentSearches = uiState.recentSearches,
                    suggestedPayees = uiState.suggestedPayees,
                    onRecentSearchClick = { viewModel.onIntent(SearchIntent.RecentSearchClicked(it)) },
                    onPayeeClick = { viewModel.onIntent(SearchIntent.PayeeClicked(it)) },
                )
            }

            is SearchResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchResult.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "No results found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            is SearchResult.Success -> {
                SearchResults(
                    accounts = uiState.accounts,
                    transactions = uiState.transactions,
                )
            }

            is SearchResult.Failure -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = (uiState.result as SearchResult.Failure).message,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        placeholder = {
            Text(
                text = "Search transactions, payees, help",
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                    )
                }
            }
        },
        shape = RoundedCornerShape(999.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
    )
}

@Composable
private fun FilterChipRow(
    selectedFilter: SearchFilter,
    onFilterSelected: (SearchFilter) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SearchFilter.entries.forEach { filter ->
            FilterChip(
                selected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = filter.label,
                        style = MaterialTheme.typography.labelLarge,
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            )
        }
    }
}

@Composable
private fun DefaultContent(
    recentSearches: List<RecentSearch>,
    suggestedPayees: List<SuggestedPayee>,
    onRecentSearchClick: (RecentSearch) -> Unit,
    onPayeeClick: (SuggestedPayee) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        item {
            SectionHeader(title = "Recent searches")
        }

        items(recentSearches, key = { it.id }) { search ->
            RecentSearchItem(
                search = search,
                onClick = { onRecentSearchClick(search) },
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            SectionHeader(title = "Suggested payees")
        }

        item {
            SuggestedPayeesRow(
                payees = suggestedPayees,
                onPayeeClick = onPayeeClick,
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
private fun RecentSearchItem(
    search: RecentSearch,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.ic_transfer),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = search.query,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun SuggestedPayeesRow(
    payees: List<SuggestedPayee>,
    onPayeeClick: (SuggestedPayee) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        payees.forEach { payee ->
            SuggestedPayeeItem(
                payee = payee,
                onClick = { onPayeeClick(payee) },
            )
        }
    }
}

@Composable
private fun SuggestedPayeeItem(
    payee: SuggestedPayee,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = payee.initials,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = payee.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SearchResults(
    accounts: List<Account>,
    transactions: List<Transaction>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        if (accounts.isNotEmpty()) {
            item {
                SectionHeader(title = "Accounts")
            }

            items(accounts, key = { it.id }) { account ->
                AccountItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    account = account,
                )
            }
        }

        if (transactions.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(title = "Transactions")
            }

            items(transactions, key = { it.id }) { transaction ->
                TransactionItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    transaction = transaction,
                )
            }
        }
    }
}

@Composable
private fun AccountItem(
    modifier: Modifier = Modifier,
    account: Account,
) {
    Card(
        modifier = modifier.clickable { },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = account.accountType.name.first().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
                Text(
                    text = account.accountNumber,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }
            Text(
                text = formatCurrency(account.balance),
                style = MaterialTheme.typography.labelLarge,
            )
        }
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
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = transaction.name.first().toString(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
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

private fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    return format.format(amount)
}

private fun formatTransactionAmount(amount: Double, isPositive: Boolean): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    val prefix = if (isPositive) "+" else ""
    return "$prefix${format.format(amount)}"
}

// region Previews

@Preview(showBackground = true)
@Composable
private fun SearchTopBarPreview() {
    IBankTheme {
        SearchTopBar()
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    IBankTheme {
        SearchBar(
            query = "Spotify",
            onQueryChange = {},
            onClearClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterChipRowPreview() {
    IBankTheme {
        FilterChipRow(
            selectedFilter = SearchFilter.ALL,
            onFilterSelected = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentSearchItemPreview() {
    IBankTheme {
        RecentSearchItem(
            search = RecentSearch("1", "Whole Foods"),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SuggestedPayeeItemPreview() {
    IBankTheme {
        SuggestedPayeeItem(
            payee = SuggestedPayee("1", "Maya", "MR"),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountItemPreview() {
    IBankTheme {
        AccountItem(
            modifier = Modifier.padding(16.dp),
            account = Account(
                id = "1",
                name = "Main Account",
                balance = 24318.52,
                currency = "USD",
                accountNumber = "****1234",
                accountType = AccountType.SAVINGS,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionItemPreview() {
    IBankTheme {
        TransactionItem(
            modifier = Modifier.padding(16.dp),
            transaction = Transaction(
                id = "1",
                name = "Spotify",
                description = "Subscription · Today",
                amount = -10.99,
                isPositive = false,
                icon = TransactionIcon.MUSIC,
                date = "Today",
            ),
        )
    }
}

@Preview(showBackground = true, heightDp = 500)
@Composable
private fun DefaultContentPreview() {
    IBankTheme {
        DefaultContent(
            recentSearches = listOf(
                RecentSearch("1", "Whole Foods"),
                RecentSearch("2", "Send to Maya"),
                RecentSearch("3", "Statement October"),
            ),
            suggestedPayees = listOf(
                SuggestedPayee("1", "Maya", "MR"),
                SuggestedPayee("2", "Daniel", "DK"),
                SuggestedPayee("3", "Sara", "SL"),
            ),
            onRecentSearchClick = {},
            onPayeeClick = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun SearchResultsPreview() {
    IBankTheme {
        SearchResults(
            accounts = listOf(
                Account("1", "Main Account", 24318.52, "USD", "****1234", AccountType.SAVINGS),
            ),
            transactions = listOf(
                Transaction("1", "Spotify", "Subscription", -10.99, false, TransactionIcon.MUSIC, "Today"),
                Transaction("2", "Salary", "Deposit", 4200.00, true, TransactionIcon.SALARY, "Yesterday"),
            ),
        )
    }
}

// endregion
