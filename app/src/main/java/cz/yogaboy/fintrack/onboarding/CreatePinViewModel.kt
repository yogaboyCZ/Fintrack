package cz.yogaboy.fintrack.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreatePinUiState(
    val digits: List<Int?> = listOf(null, null, null, null),
    val maskDigit: List<Boolean> = listOf(false, false, false, false),
    val isSubmitting: Boolean = false
) {
    val isComplete: Boolean get() = digits.none { it == null }
    val pinOrNull: String? get() = if (isComplete) digits.joinToString("") else null
}
sealed interface CreatePinEvent {
    data class DigitPressed(val digit: Int) : CreatePinEvent
    data object BackspacePressed : CreatePinEvent
    data object SubmitPressed : CreatePinEvent
    data object BackClicked : CreatePinEvent
}

sealed interface CreatePinEffect {
    data object NavigateBack : CreatePinEffect
    data class PinCreated(val pin: String) : CreatePinEffect
}

class CreatePinViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePinUiState())
    val uiState: StateFlow<CreatePinUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<CreatePinEffect>()
    val uiEffect: SharedFlow<CreatePinEffect> = _uiEffect.asSharedFlow()

    fun handle(event: CreatePinEvent) {
        when (event) {
            is CreatePinEvent.DigitPressed -> onDigit(event.digit)
            CreatePinEvent.BackspacePressed -> onBackspace()
            CreatePinEvent.SubmitPressed -> onSubmit()
            CreatePinEvent.BackClicked -> viewModelScope.launch {
                _uiEffect.emit(CreatePinEffect.NavigateBack)
            }
        }
    }

    private fun onDigit(pressedNumber: Int) {
        if (pressedNumber !in 0..9) return
        val current = _uiState.value
        val digits = current.digits.toMutableList()
        val mask = current.maskDigit.toMutableList()

        val index = digits.indexOfFirst { it == null }
        if (index != -1) {
            digits[index] = pressedNumber
            mask[index] = true
            _uiState.update { it.copy(digits = digits, maskDigit = mask) }

            viewModelScope.launch {
                delay(1000)
                val maskCopy = _uiState.value.maskDigit.toMutableList()
                maskCopy[index] = false
                _uiState.update { it.copy(maskDigit = maskCopy) }
            }
        }
    }

    private fun onBackspace() {
        val current = _uiState.value
        val digits = current.digits.toMutableList()
        val maskCopy = current.maskDigit.toMutableList()

        val index = digits.indexOfLast { it != null }
        if (index != -1) {
            digits[index] = null
            maskCopy[index] = false
            _uiState.update { it.copy(digits = digits, maskDigit = maskCopy) }
        }
    }

    private fun onSubmit() {
        val pin = _uiState.value.pinOrNull ?: return
        viewModelScope.launch { _uiEffect.emit(CreatePinEffect.PinCreated(pin)) }
    }
}