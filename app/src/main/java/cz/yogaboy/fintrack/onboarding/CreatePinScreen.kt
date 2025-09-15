package cz.yogaboy.fintrack.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun CreatePinRoute(
    navController: NavController,
) {
    val vm: OnboardingViewModel = viewModel()
    CreatePinScreen(
        state = vm.uiState.collectAsStateWithLifecycle().value,
        onClick = vm::handleEvent
    )
}

@Composable
fun CreatePinScreen(
    state: OnboardingUiState,
    onClick: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create PIN Screen")
    }
}