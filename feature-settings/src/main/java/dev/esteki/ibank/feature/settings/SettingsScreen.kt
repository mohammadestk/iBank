package dev.esteki.ibank.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.esteki.ibank.core.domain.model.SettingsItem
import dev.esteki.ibank.core.domain.model.SettingsItemType
import dev.esteki.ibank.core.domain.model.SettingsSection
import dev.esteki.ibank.core.domain.model.UserProfile
import dev.esteki.ibank.core.presentation.R
import dev.esteki.ibank.core.presentation.theme.IBankTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.result) {
        is SettingsResult.Idle, is SettingsResult.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is SettingsResult.Success -> {
            SettingsContent(
                modifier = modifier,
                profile = uiState.profile,
                sections = uiState.sections,
                onItemClick = { viewModel.onIntent(SettingsIntent.ItemClicked(it)) },
            )
        }

        is SettingsResult.Failure -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = (uiState.result as SettingsResult.Failure).message,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier,
    profile: UserProfile?,
    sections: List<SettingsSection>,
    onItemClick: (SettingsItem) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }

        if (profile != null) {
            item {
                ProfileRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    profile = profile,
                )
            }
        }

        items(sections) { section ->
            SettingsSection(
                section = section,
                onItemClick = onItemClick,
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            SignOutButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { /* TODO: Sign out */ },
            )
        }
    }
}

@Composable
private fun ProfileRow(
    modifier: Modifier = Modifier,
    profile: UserProfile,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = profile.name.take(2).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = profile.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                text = "View profile ›",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        }
    }
}

@Composable
private fun SettingsSection(
    section: SettingsSection,
    onItemClick: (SettingsItem) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(
            text = section.title.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = MaterialTheme.typography.labelSmall.letterSpacing,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            modifier = Modifier.padding(bottom = 8.dp),
        )

        section.items.forEachIndexed { index, item ->
            SettingsItemRow(
                item = item,
                onClick = { onItemClick(item) },
            )
            if (index < section.items.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(start = 46.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
            }
        }
    }
}

@Composable
private fun SettingsItemRow(
    item: SettingsItem,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(17.dp),
                painter = painterResource(item.iconRes),
                contentDescription = item.label,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            val subtitle = item.subtitle
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }
        }
        when (item.type) {
            SettingsItemType.NAVIGATION -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            SettingsItemType.TOGGLE -> {
                var checked by remember { mutableStateOf(item.isChecked) }
                Switch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.surface,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            }
            SettingsItemType.INFO -> {
                // No action indicator
            }
        }
    }
}

@Composable
private fun SignOutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Text(
        text = "Sign out",
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold,
        ),
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .fillMaxWidth(),
    )
}

// region Previews

@Preview(showBackground = true)
@Composable
private fun ProfileRowPreview() {
    IBankTheme {
        ProfileRow(
            modifier = Modifier.padding(16.dp),
            profile = UserProfile(
                id = "1",
                name = "Mohammad Esteki",
                avatarUrl = "",
                notificationCount = 5,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsSectionPreview() {
    IBankTheme {
        SettingsSection(
            section = SettingsSection(
                id = "security",
                title = "Security",
                items = listOf(
                    SettingsItem("biometric", "Biometric login", R.drawable.ic_send, SettingsItemType.TOGGLE, subtitle = "Face ID enabled", isChecked = true),
                    SettingsItem("2fa", "Two-factor authentication", R.drawable.ic_transfer, SettingsItemType.NAVIGATION),
                    SettingsItem("pin", "Change PIN", R.drawable.ic_request, SettingsItemType.NAVIGATION),
                ),
            ),
            onItemClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemRowNavigationPreview() {
    IBankTheme {
        SettingsItemRow(
            item = SettingsItem("2fa", "Two-factor authentication", R.drawable.ic_transfer, SettingsItemType.NAVIGATION),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemRowTogglePreview() {
    IBankTheme {
        SettingsItemRow(
            item = SettingsItem("biometric", "Biometric login", R.drawable.ic_send, SettingsItemType.TOGGLE, subtitle = "Face ID enabled", isChecked = true),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignOutButtonPreview() {
    IBankTheme {
        SignOutButton(
            modifier = Modifier.padding(16.dp),
            onClick = {},
        )
    }
}

// endregion
