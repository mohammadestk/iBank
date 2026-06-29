package dev.esteki.ibank

import kotlinx.serialization.Serializable

@Serializable
sealed class BottomRoute(
    val label: String,
    val icon: Int,
    val contentDescription: String
) {

    @Serializable
    data object Home : BottomRoute(
        label = "Home",
        icon = R.drawable.home,
        contentDescription = "Home",
    )

    @Serializable
    data object Search : BottomRoute(
        label = "Search",
        icon = R.drawable.search,
        contentDescription = "Search",
    )

    @Serializable
    data object Message : BottomRoute(
        label = "Message",
        icon = R.drawable.message,
        contentDescription = "Message",
    )

    @Serializable
    data object Settings : BottomRoute(
        label = "Settings",
        icon = R.drawable.settings,
        contentDescription = "Settings",
    )
}