package cz.yogaboy.fintrack.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface OnboardingEvent {
    data object GoToCreatePin : OnboardingEvent
}

data object OnboardingUiState

sealed interface OnboardingUiEffect {
    data object NavigateToCreatePin : OnboardingUiEffect
}

class OnboardingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUiState)
    val uiState: StateFlow<OnboardingUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<OnboardingUiEffect>()
    val uiEffect: SharedFlow<OnboardingUiEffect> = _uiEffect

    fun handleEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.GoToCreatePin -> {
                viewModelScope.launch { _uiEffect.emit(OnboardingUiEffect.NavigateToCreatePin) }
            }
        }
    }
}