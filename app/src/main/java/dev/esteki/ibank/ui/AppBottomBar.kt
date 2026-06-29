package dev.esteki.ibank.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.esteki.ibank.ui.theme.IBankTheme

@Composable
fun AppBottomBar(onItemClick: (BottomRoute) -> Unit) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        BottomDestinations.forEachIndexed { index, route ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    onItemClick(route)
                },
                label = {
                    Text(text = route.label)
                },
                icon = {
                    Icon(
                        painter = painterResource(route.icon),
                        contentDescription = route.contentDescription
                    )
                }
            )
        }

    }
}

@Preview
@Composable
private fun AppBottomBarPreview() {
    IBankTheme {
        AppBottomBar(onItemClick = {})
    }
}