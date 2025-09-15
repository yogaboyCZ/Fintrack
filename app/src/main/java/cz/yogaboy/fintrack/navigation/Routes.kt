package cz.yogaboy.fintrack.navigation

sealed interface Route {
    val route: String
}

object Graph {
    const val ONBOARDING = "onboarding"
}

data object OnboardingStart : Route {
    override val route: String = "onboarding/start"
}

data object OnboardingCreatePin : Route {
    override val route: String = "onboarding/createPin"
}