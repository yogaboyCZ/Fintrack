package cz.yogaboy.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import cz.yogaboy.dashboard.DashboardRoute

@Serializable object Dashboard

fun NavGraphBuilder.dashboardNavGraph() {
    composable<Dashboard> { DashboardRoute() }
}