package cz.yogaboy.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface OnboardingEvent {

}

data class OnboardingUiState(val placeholder: Boolean = true)

sealed interface OnboardingUiEffect {
    data object NavigateToDashboard : OnboardingUiEffect
}

class OnboardingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<OnboardingUiEffect>()
    val uiEffect: SharedFlow<OnboardingUiEffect> = _uiEffect

    fun handleEvent(event: OnboardingEvent) {

    }
}
