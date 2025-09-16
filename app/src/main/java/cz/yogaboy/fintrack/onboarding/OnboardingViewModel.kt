package cz.yogaboy.fintrack.onboarding

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.yogaboy.fintrack.onboarding.model.OnboardingOptionUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface OnboardingEvent {
    data object GoToCreatePin : OnboardingEvent
    data object LinkBank : OnboardingEvent
    data object SavingGoals : OnboardingEvent
    data object ExitProcess : OnboardingEvent
}

data class OnboardingUiState(
    val options: List<OnboardingOptionUIModel>,
    @StringRes val exitButton: Int,
)

sealed interface OnboardingUiEffect {
    data object NavigateToCreatePin : OnboardingUiEffect
}

class OnboardingViewModel(
    private val uiProvider: OnboardingStartUiProvider = DefaultOnboardingStartUiProvider()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        OnboardingUiState(
            options = uiProvider.options(),
            exitButton = uiProvider.exitButton(),
        )
    )
    val uiState: StateFlow<OnboardingUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<OnboardingUiEffect>()
    val uiEffect: SharedFlow<OnboardingUiEffect> = _uiEffect

    fun handleEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.GoToCreatePin -> {
                viewModelScope.launch { _uiEffect.emit(OnboardingUiEffect.NavigateToCreatePin) }
            }
            OnboardingEvent.LinkBank -> Unit
            OnboardingEvent.SavingGoals -> Unit
            OnboardingEvent.ExitProcess -> Unit
        }
    }
}