package dev.esteki.ibank.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.esteki.ibank.theme.IBankTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        HomeTopBar(
            modifier = Modifier.fillMaxWidth(),
            avatar = "https://picsum.photos/200",
            title = "Hi, Mohammad Esteki!",
            notificationCount = 5,
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    IBankTheme {
        HomeScreen(modifier = Modifier.fillMaxSize())
    }
}