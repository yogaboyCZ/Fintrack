package cz.yogaboy.account.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import cz.yogaboy.account.AccountStartRoute
import cz.yogaboy.account.CreatePinRoute
import kotlinx.serialization.Serializable

@Serializable object AccountGraph
@Serializable object AccountStart
@Serializable object AccountCreatePin

fun NavGraphBuilder.accountNavGraph(
    navController: NavHostController,
    onExitToDashboard: () -> Unit,
) {
    navigation<AccountGraph>(startDestination = AccountStart) {
        composable<AccountStart> {
            AccountStartRoute(
                navController = navController,
                onExitToDashboard = onExitToDashboard
            )
        }
        composable<AccountCreatePin> {
            CreatePinRoute(navController = navController)
        }
    }
}