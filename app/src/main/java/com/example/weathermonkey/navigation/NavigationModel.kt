package com.example.weathermonkey.navigation

import androidx.annotation.DrawableRes
import com.example.weathermonkey.R
import kotlinx.serialization.Serializable

enum class NavModel(
    val route: Any,
    val label: String,
    @DrawableRes
    val icon: Int,
) {
    First(
        route =  HomeView,
        label =  "Weather",
        icon = R.drawable.baseline_sunny_snowing_24),
    Second(
        route = HistoryView,
        label ="Details",
        icon = R.drawable.baseline_history_24),

}

@Serializable
object HomeView

@Serializable
object HistoryView

