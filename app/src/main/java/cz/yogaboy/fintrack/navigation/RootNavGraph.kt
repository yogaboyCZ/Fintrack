package cz.yogaboy.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Graph.ONBOARDING,
    ) {
        onboardingNavGraph(navController = navController)
    }
}