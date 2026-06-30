package dev.esteki.ibank.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun IconWithBadge(
    modifier: Modifier = Modifier,
    icon: Int,
    badgeCount: Int? = null,
    contentDescription: String? = null,
    iconTint: Color = LocalContentColor.current,
    badgeColor: Color = MaterialTheme.colorScheme.error,
    onClick: (() -> Unit)? = null,
) {
    val iconContent: @Composable () -> Unit = {
        BadgedBox(
            badge = {
                if (badgeCount != null && badgeCount > 0) {
                    Badge(containerColor = badgeColor) {
                        Text(
                            text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                } else if (badgeCount == null) {
                    // dot-only badge (e.g. "new notification" indicator)
                    Badge(containerColor = badgeColor)
                }
            }
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = contentDescription,
                tint = iconTint
            )
        }
    }

    if (onClick != null) {
        IconButton(onClick = onClick, modifier = modifier) { iconContent() }
    } else {
        Box(modifier = modifier) { iconContent() }
    }
}
