package cz.yogaboy.account

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreatePinViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is empty`() = runTest {
        val viewModel = CreatePinViewModel()
        val uiState = viewModel.uiState.value
        Assertions.assertEquals(listOf(null, null, null, null), uiState.digits)
        Assertions.assertEquals(listOf(false, false, false, false), uiState.maskDigit)
        Assertions.assertFalse(uiState.isComplete)
    }

    @Test
    fun `DigitPressed fills next slot and masks for 1s`() = runTest {
        val vm = CreatePinViewModel()

        vm.handle(CreatePinEvent.DigitPressed(1))
        vm.handle(CreatePinEvent.DigitPressed(2))

        Assertions.assertEquals(listOf(1, 2, null, null), vm.uiState.value.digits)
        Assertions.assertEquals(listOf(true, true, false, false), vm.uiState.value.maskDigit)

        advanceUntilIdle()

        Assertions.assertEquals(listOf(false, false, false, false), vm.uiState.value.maskDigit)
    }

    @Test
    fun `Backspace clears last non-null and unmasks it`() = runTest {
        val vm = CreatePinViewModel()
        vm.handle(CreatePinEvent.DigitPressed(7))
        vm.handle(CreatePinEvent.DigitPressed(8))
        Assertions.assertEquals(listOf(7, 8, null, null), vm.uiState.value.digits)

        vm.handle(CreatePinEvent.BackspacePressed)

        Assertions.assertEquals(listOf(7, null, null, null), vm.uiState.value.digits)
        Assertions.assertEquals(listOf(true, false, false, false), vm.uiState.value.maskDigit)
    }

    @Test
    fun `BackClicked emits NavigateBack`() = runTest {
        val vm = CreatePinViewModel()
        vm.uiEffect.test {
            vm.handle(CreatePinEvent.BackClicked)
            Assertions.assertEquals(CreatePinEffect.NavigateBack, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}