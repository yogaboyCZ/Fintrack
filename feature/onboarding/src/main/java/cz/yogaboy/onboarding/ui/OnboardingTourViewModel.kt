package cz.yogaboy.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OnboardingTourState(
    val pageCount: Int = 3,
    val currentPage: Int = 0,
    val showSheet: Boolean = false
)

sealed interface OnboardingTourEvent {
    data class PageChanged(val index: Int) : OnboardingTourEvent
    data object ShowBottomSheet : OnboardingTourEvent
    data object BottomSheetDismissed : OnboardingTourEvent
    data object NavigateToCreateAccount : OnboardingTourEvent
}

sealed interface OnboardingTourEffect {
    data object NavigateToCreateAccount : OnboardingTourEffect
}

class OnboardingTourViewModel : ViewModel() {

    private val _state = MutableStateFlow(OnboardingTourState())
    val state: StateFlow<OnboardingTourState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<OnboardingTourEffect>()
    val effect: SharedFlow<OnboardingTourEffect> = _effect.asSharedFlow()

    fun handle(event: OnboardingTourEvent) {
        when (event) {
            is OnboardingTourEvent.PageChanged ->
                _state.value =
                    _state.value.copy(currentPage = event.index) // Now we don't use this but state is updated anyway

            OnboardingTourEvent.ShowBottomSheet ->
                _state.value = _state.value.copy(showSheet = true)

            OnboardingTourEvent.BottomSheetDismissed ->
                _state.value = _state.value.copy(showSheet = false)

            OnboardingTourEvent.NavigateToCreateAccount -> {
                _state.value = _state.value.copy(showSheet = false)
                viewModelScope.launch {
                    _effect.emit(OnboardingTourEffect.NavigateToCreateAccount)
                }
            }
        }
    }
}