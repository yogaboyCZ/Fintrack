package cz.yogaboy.fintrack.onboarding

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    @Test
    fun `GoToCreatePin event emits NavigateToCreatePin effect`() = runTest {
        val vm = OnboardingViewModel()

        vm.uiEffect.test {
            vm.handleEvent(OnboardingEvent.GoToCreatePin)

            assertEquals(
                OnboardingUiEffect.NavigateToCreatePin,
                awaitItem()
            )
            cancelAndConsumeRemainingEvents()
        }
    }
}