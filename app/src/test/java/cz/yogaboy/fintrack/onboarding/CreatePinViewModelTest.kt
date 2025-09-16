package cz.yogaboy.fintrack.onboarding

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreatePinViewModelTest {

    @Test
    fun `initial state is empty`() = runTest {
        val vm = CreatePinViewModel()
        val s = vm.uiState.value
        assertEquals(listOf(null, null, null, null), s.digits)
        assertEquals(listOf(false, false, false, false), s.maskDigit)
        assertFalse(s.isComplete)
    }

    @Test
    fun `DigitPressed fills next slot and masks for 1s`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        try {
            val vm = CreatePinViewModel()

            vm.handle(CreatePinEvent.DigitPressed(1))
            vm.handle(CreatePinEvent.DigitPressed(2))

            assertEquals(listOf(1, 2, null, null), vm.uiState.value.digits)
            assertEquals(listOf(true, true, false, false), vm.uiState.value.maskDigit)

            advanceUntilIdle()

            assertEquals(listOf(false, false, false, false), vm.uiState.value.maskDigit)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `Backspace clears last non-null and unmasks it`() = runTest {
        val vm = CreatePinViewModel()
        vm.handle(CreatePinEvent.DigitPressed(7))
        vm.handle(CreatePinEvent.DigitPressed(8))
        assertEquals(listOf(7, 8, null, null), vm.uiState.value.digits)

        vm.handle(CreatePinEvent.BackspacePressed)

        assertEquals(listOf(7, null, null, null), vm.uiState.value.digits)
        assertEquals(listOf(true, false, false, false), vm.uiState.value.maskDigit)

        advanceUntilIdle()
        assertEquals(listOf(true, false, false, false), vm.uiState.value.maskDigit)
    }

    @Test
    fun `BackClicked emits NavigateBack`() = runTest {
        val vm = CreatePinViewModel()
        vm.uiEffect.test {
            vm.handle(CreatePinEvent.BackClicked)
            assertEquals(CreatePinEffect.NavigateBack, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}