package cz.yogaboy.fintrack.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cz.yogaboy.fintrack.onboarding.CreatePinRoute
import cz.yogaboy.fintrack.onboarding.OnboardingStartRoute

fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController) {
    navigation(
        startDestination = OnboardingStart.route,
        route = Graph.ONBOARDING
    ) {
        composable(OnboardingStart.route) {
            OnboardingStartRoute(navController = navController)
        }
        composable(OnboardingCreatePin.route) {
            CreatePinRoute(navController = navController)
        }
    }
}