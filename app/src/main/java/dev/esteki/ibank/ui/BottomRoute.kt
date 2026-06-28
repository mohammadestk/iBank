package dev.esteki.ibank.ui

import dev.esteki.ibank.R

sealed class BottomRoute(
    val label: String,
    val icon: Int,
    val contentDescription: String
) {
    data object Home : BottomRoute(
        label = "Home",
        icon = R.drawable.home,
        contentDescription = "Home",
    )

    data object Search : BottomRoute(
        label = "Search",
        icon = R.drawable.search,
        contentDescription = "Search",
    )

    data object Message : BottomRoute(
        label = "Message",
        icon = R.drawable.message,
        contentDescription = "Messages",
    )

    data object Settings : BottomRoute(
        label = "Settings",
        icon = R.drawable.settings,
        contentDescription = "Settings",
    )
}

val BottomDestinations: List<BottomRoute> = listOf(
    BottomRoute.Home,
    BottomRoute.Search,
    BottomRoute.Message,
    BottomRoute.Settings
)