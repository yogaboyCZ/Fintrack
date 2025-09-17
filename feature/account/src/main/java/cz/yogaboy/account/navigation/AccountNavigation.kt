package cz.yogaboy.account.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cz.yogaboy.account.CreatePinRoute
import cz.yogaboy.account.OnboardingStartRoute

object AccountRoutes {
    const val ROOT = "account"
    const val START = "account/start"
    const val CREATE_PIN = "account/create_pin"
}

fun NavGraphBuilder.accountNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AccountRoutes.START,
        route = AccountRoutes.ROOT
    ) {
        composable(AccountRoutes.START) { OnboardingStartRoute(navController) }
        composable(AccountRoutes.CREATE_PIN) { CreatePinRoute(navController) }
    }
}