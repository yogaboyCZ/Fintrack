package cz.yogaboy.fintrack.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cz.yogaboy.fintrack.navigation.OnboardingCreatePin

@Composable
fun OnboardingStartRoute(
    navController: NavController,
) {
    val vm: OnboardingViewModel = viewModel()

    LaunchedEffect(Unit) {
        vm.uiEffect.collect { effect ->
            when (effect) {
                OnboardingUiEffect.NavigateToCreatePin -> navController.navigate(OnboardingCreatePin.route)
            }
        }
    }

    OnboardingStartScreen(
        state = vm.uiState.collectAsStateWithLifecycle().value,
        onClick = vm::handleEvent
    )
}

@Composable
fun OnboardingStartScreen(
    state: OnboardingUiState,
    onClick: (OnboardingEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onClick(OnboardingEvent.GoToCreatePin) }) {
            Text("Go to Create PIN")
        }
    }
}


@Preview
@Composable
fun OnboardingStartScreenPreview() {
    OnboardingStartScreen(
        state = OnboardingUiState,
        onClick = {}
    )
}