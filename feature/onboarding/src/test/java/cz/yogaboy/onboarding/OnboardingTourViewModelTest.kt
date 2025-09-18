package cz.yogaboy.onboarding

import app.cash.turbine.test
import cz.yogaboy.onboarding.ui.OnboardingTourEffect
import cz.yogaboy.onboarding.ui.OnboardingTourEvent
import cz.yogaboy.onboarding.ui.OnboardingTourViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingTourViewModelTest {

    private lateinit var viewModel: OnboardingTourViewModel

    @BeforeEach
    fun setup() {
        viewModel = OnboardingTourViewModel()
    }

    @Test
    fun `PageChanged updates currentPage in state`() = runTest {
        viewModel.handle(OnboardingTourEvent.PageChanged(2))
        assertEquals(2, viewModel.state.value.currentPage)
    }

    @Test
    fun `ShowBottomSheet sets showSheet true`() = runTest {
        viewModel.handle(OnboardingTourEvent.ShowBottomSheet)
        assertEquals(true, viewModel.state.value.showSheet)
    }

    @Test
    fun `BottomSheetDismissed sets showSheet false`() = runTest {
        viewModel.handle(OnboardingTourEvent.ShowBottomSheet)
        assertEquals(true, viewModel.state.value.showSheet)

        viewModel.handle(OnboardingTourEvent.BottomSheetDismissed)
        assertEquals(false, viewModel.state.value.showSheet)
    }

    @Test
    fun `NavigateToCreateAccount emits effect and closes sheet`() = runTest {
        viewModel.handle(OnboardingTourEvent.ShowBottomSheet)
        assertEquals(true, viewModel.state.value.showSheet)

        viewModel.effect.test {
            viewModel.handle(OnboardingTourEvent.NavigateToCreateAccount)

            assertEquals(false, viewModel.state.value.showSheet)
            assertEquals(OnboardingTourEffect.NavigateToCreateAccount, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}