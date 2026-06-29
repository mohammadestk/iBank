package dev.esteki.ibank.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.esteki.ibank.BottomRoute
import dev.esteki.ibank.theme.IBankTheme

private val PreviewDestinations = listOf(
    BottomRoute.Home,
    BottomRoute.Search,
    BottomRoute.Message,
    BottomRoute.Settings,
)

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    selectedRoute: BottomRoute,
    onItemClick: (BottomRoute) -> Unit,
    items: List<BottomRoute>,
) {
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item == selectedRoute,
                onClick = {
                    onItemClick(item)
                },
                label = {
                    Text(text = item.label)
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.contentDescription
                    )
                },
                alwaysShowLabel = false,
            )
        }
    }
}

@Preview
@Composable
private fun AppBottomBarPreview() {
    IBankTheme {
        var selectedRoute by remember { mutableStateOf(PreviewDestinations.first()) }

        AppBottomBar(
            selectedRoute = selectedRoute,
            onItemClick = {
                selectedRoute = it
            },
            items = PreviewDestinations,
        )
    }
}