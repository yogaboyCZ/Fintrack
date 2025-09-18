package cz.yogaboy.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cz.yogaboy.onboarding.ui.OnboardingTourRoute
import kotlinx.serialization.Serializable

@Serializable object OnboardingGraph
@Serializable object OnboardingTour

fun NavGraphBuilder.onboardingNavGraph(
    onCreateAccount: () -> Unit,
) {
    navigation<OnboardingGraph>(startDestination = OnboardingTour) {
        composable<OnboardingTour> {
            OnboardingTourRoute(onCreateAccount = onCreateAccount)
        }
    }
}