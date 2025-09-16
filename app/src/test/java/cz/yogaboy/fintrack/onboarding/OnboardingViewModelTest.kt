package cz.yogaboy.fintrack.onboarding

import app.cash.turbine.test
import cz.yogaboy.fintrack.onboarding.model.OnboardingOptionUIModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OnboardingViewModelTest {


    @Test
    fun `GoToCreatePin emits NavigateToCreatePin effect`() = runTest {
        val vm = OnboardingViewModel(uiProvider = FakeOnboardingStartUiProvider())
        vm.uiEffect.test {
            vm.handleEvent(OnboardingEvent.GoToCreatePin)
            assertEquals(OnboardingUiEffect.NavigateToCreatePin, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `LinkBank does not emit navigation effect yet`() = runTest {
        val vm = OnboardingViewModel(uiProvider = FakeOnboardingStartUiProvider())
        vm.uiEffect.test {
            vm.handleEvent(OnboardingEvent.LinkBank)
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `SavingGoals does not emit navigation effect yet`() = runTest {
        val vm = OnboardingViewModel(uiProvider = FakeOnboardingStartUiProvider())
        vm.uiEffect.test {
            vm.handleEvent(OnboardingEvent.SavingGoals)
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `ExitProcess does not emit navigation effect yet`() = runTest {
        val vm = OnboardingViewModel(uiProvider = FakeOnboardingStartUiProvider())
        vm.uiEffect.test {
            vm.handleEvent(OnboardingEvent.ExitProcess)
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }
}


class FakeOnboardingStartUiProvider : OnboardingStartUiProvider {
    override fun options(): List<OnboardingOptionUIModel> = listOf(
        OnboardingOptionUIModel(
            event = OnboardingEvent.GoToCreatePin,
            titleRes = 1,
            subtitleRes = 2,
            imageRes = 3
        ),
        OnboardingOptionUIModel(
            event = OnboardingEvent.LinkBank,
            titleRes = 4,
            subtitleRes = 5,
            imageRes = 6
        ),
        OnboardingOptionUIModel(
            event = OnboardingEvent.SavingGoals,
            titleRes = 7,
            subtitleRes = 8,
            imageRes = 9
        )
    )

    override fun exitButton(): Int = 10
}