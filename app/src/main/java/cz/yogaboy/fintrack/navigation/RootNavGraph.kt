package cz.yogaboy.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cz.yogaboy.account.navigation.AccountGraph
import cz.yogaboy.account.navigation.accountNavGraph
import cz.yogaboy.dashboard.navigation.Dashboard
import cz.yogaboy.dashboard.navigation.dashboardNavGraph
import cz.yogaboy.onboarding.navigation.OnboardingGraph
import cz.yogaboy.onboarding.navigation.onboardingNavGraph

@Composable
fun RootNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OnboardingGraph
    ) {
        onboardingNavGraph(
            onCreateAccount = {
                navController.navigate(AccountGraph) {
                    popUpTo(OnboardingGraph) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        accountNavGraph(
            navController = navController,
            onExitToDashboard = {
                navController.navigate(Dashboard) {
                    popUpTo(AccountGraph) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        dashboardNavGraph()
    }
}