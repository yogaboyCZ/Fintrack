package cz.yogaboy.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cz.yogaboy.account.navigation.AccountRoutes
import cz.yogaboy.account.navigation.accountNavGraph

@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AccountRoutes.ROOT
    ) {
        accountNavGraph(navController)
    }
}