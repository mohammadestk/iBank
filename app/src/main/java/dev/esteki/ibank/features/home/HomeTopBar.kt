package dev.esteki.ibank.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.esteki.ibank.R
import dev.esteki.ibank.components.IconWithBadge
import dev.esteki.ibank.theme.IBankTheme
import dev.esteki.ibank.theme.iTypography

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    avatar: Any?,
    title: String,
    notificationCount: Int,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 8.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = avatar,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = title,
                style = MaterialTheme.iTypography.body1.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        }

        IconWithBadge(
            icon = R.drawable.ring,
            badgeCount = notificationCount,
            contentDescription = "Notifications",
            onClick = { },
            iconTint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview
@Composable
private fun HomeTopBarPreview() {
    IBankTheme {
        HomeTopBar(
            modifier = Modifier.fillMaxWidth(),
            avatar = "https://picsum.photos/200",
            title = "Hello, User!",
            notificationCount = 5
        )
    }
}