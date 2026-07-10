package dev.esteki.ibank.feature.message.ui

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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.esteki.ibank.core.domain.message.model.Message
import dev.esteki.ibank.core.domain.message.model.MessageType
import dev.esteki.ibank.core.presentation.theme.IBankTheme
import dev.esteki.ibank.feature.message.model.MessageFilter
import dev.esteki.ibank.feature.message.model.MessageIntent
import dev.esteki.ibank.feature.message.model.MessageResult
import dev.esteki.ibank.feature.message.model.MessageUiState
import dev.esteki.ibank.feature.message.viewmodel.MessageViewModel
import androidx.annotation.VisibleForTesting

@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        MessageTopBar(
            onSearchClick = { /* TODO: Navigate to search */ },
        )

        SegmentedFilterRow(
            selectedFilter = uiState.selectedFilter,
            onFilterSelected = { viewModel.onIntent(MessageIntent.FilterSelected(it)) },
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (uiState.result) {
            is MessageResult.Idle, is MessageResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is MessageResult.Success -> {
                MessageList(
                    messages = uiState.filteredMessages,
                    onMessageClick = { viewModel.onIntent(MessageIntent.MessageClicked(it)) },
                )
            }

            is MessageResult.Failure -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = (uiState.result as MessageResult.Failure).message,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageTopBar(
    onSearchClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Messages",
            style = MaterialTheme.typography.headlineMedium,
        )
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search messages",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
@VisibleForTesting
internal fun SegmentedFilterRow(
    selectedFilter: MessageFilter,
    onFilterSelected: (MessageFilter) -> Unit,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        MessageFilter.entries.forEachIndexed { index, filter ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = MessageFilter.entries.size,
                ),
                onClick = { onFilterSelected(filter) },
                selected = filter == selectedFilter,
                label = {
                    Text(
                        text = filter.label,
                        style = MaterialTheme.typography.labelLarge,
                    )
                },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.surface,
                    activeContentColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
            )
        }
    }
}

@Composable
@VisibleForTesting
internal fun MessageList(
    messages: List<Message>,
    onMessageClick: (Message) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        items(messages, key = { it.id }) { message ->
            MessageCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                message = message,
                onClick = { onMessageClick(message) },
            )
        }
    }
}

@Composable
private fun MessageCard(
    modifier: Modifier = Modifier,
    message: Message,
    onClick: () -> Unit,
) {
    val isAlert = message.type == MessageType.SECURITY

    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isAlert) {
                MaterialTheme.colorScheme.tertiaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            },
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.07f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = getMessageTypeIcon(message.type),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = message.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = message.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                        ),
                        maxLines = 2,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = message.timestamp,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        ),
                    )
                }
            }

            if (!message.isRead) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 14.dp, end = 12.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                )
            }
        }
    }
}

private fun getMessageTypeIcon(type: MessageType): String {
    return when (type) {
        MessageType.TRANSACTION -> "$"
        MessageType.SECURITY -> "!"
        MessageType.PROMOTION -> "%"
        MessageType.SYSTEM -> "i"
    }
}

// region Previews

@Preview(showBackground = true)
@Composable
private fun MessageTopBarPreview() {
    IBankTheme {
        MessageTopBar(onSearchClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun SegmentedFilterRowPreview() {
    IBankTheme {
        SegmentedFilterRow(
            selectedFilter = MessageFilter.INBOX,
            onFilterSelected = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageCardAlertPreview() {
    IBankTheme {
        MessageCard(
            modifier = Modifier.padding(16.dp),
            message = Message(
                id = "1",
                title = "Fraud alert",
                description = "We blocked a suspicious charge of \$412.00 in Lagos. Confirm if this was you.",
                timestamp = "2h ago",
                type = MessageType.SECURITY,
                isRead = false,
            ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageCardNormalPreview() {
    IBankTheme {
        MessageCard(
            modifier = Modifier.padding(16.dp),
            message = Message(
                id = "2",
                title = "November statement ready",
                description = "Your statement is available to download in Documents.",
                timestamp = "1d ago",
                type = MessageType.SYSTEM,
                isRead = true,
            ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
private fun MessageListPreview() {
    IBankTheme {
        MessageList(
            messages = listOf(
                Message("1", "Fraud alert", "We blocked a suspicious charge of \$412.00", "2h ago", MessageType.SECURITY, false),
                Message("2", "November statement ready", "Your statement is available to download", "1d ago", MessageType.SYSTEM, true),
                Message("3", "Support · Card replacement", "Your new card ships within 3–5 business days", "3d ago", MessageType.PROMOTION, true),
            ),
            onMessageClick = {},
        )
    }
}

// endregion
