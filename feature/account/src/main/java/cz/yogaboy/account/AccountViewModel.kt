package cz.yogaboy.account

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.yogaboy.account.model.OnboardingOptionUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AccountEvent {
    data object GoToCreatePin : AccountEvent
    data object LinkBank : AccountEvent
    data object SavingGoals : AccountEvent
    data object ExitProcess : AccountEvent
    data object SkipForNow : AccountEvent

}

data class AccountUiState(
    val options: List<OnboardingOptionUIModel>,
)

sealed interface AccountUiEffect {
    data object NavigateToCreatePin : AccountUiEffect
    data object NavigateToDashboard : AccountUiEffect
}

class AccountViewModel(
    private val uiProvider: AccountStartUiProvider = DefaultAccountStartUiProvider()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AccountUiState(
            options = uiProvider.options(),
        )
    )
    val uiState: StateFlow<AccountUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<AccountUiEffect>()
    val uiEffect: SharedFlow<AccountUiEffect> = _uiEffect

    fun handleEvent(event: AccountEvent) {
        when (event) {
            AccountEvent.GoToCreatePin -> {
                viewModelScope.launch { _uiEffect.emit(AccountUiEffect.NavigateToCreatePin) }
            }
            AccountEvent.SkipForNow -> viewModelScope.launch { _uiEffect.emit(AccountUiEffect.NavigateToDashboard) }

            AccountEvent.LinkBank -> Unit
            AccountEvent.SavingGoals -> Unit
            AccountEvent.ExitProcess -> Unit
        }
    }
}