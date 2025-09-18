package cz.yogaboy.account

import app.cash.turbine.test
import cz.yogaboy.account.model.OnboardingOptionUIModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AccountViewModelTest {


    @Test
    fun `GoToCreatePin emits NavigateToCreatePin effect`() = runTest {
        val vm = AccountViewModel(uiProvider = FakeAccountStartUiProvider())
        vm.uiEffect.test {
            vm.handleEvent(AccountEvent.GoToCreatePin)
            assertEquals(AccountUiEffect.NavigateToCreatePin, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `LinkBank does not emit navigation effect yet`() = runTest {
        val vm = AccountViewModel(uiProvider = FakeAccountStartUiProvider())
        vm.uiEffect.test {
            vm.handleEvent(AccountEvent.LinkBank)
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `SavingGoals does not emit navigation effect yet`() = runTest {
        val vm = AccountViewModel(uiProvider = FakeAccountStartUiProvider())
        vm.uiEffect.test {
            vm.handleEvent(AccountEvent.SavingGoals)
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `ExitProcess does not emit navigation effect yet`() = runTest {
        val vm = AccountViewModel(uiProvider = FakeAccountStartUiProvider())
        vm.uiEffect.test {
            vm.handleEvent(AccountEvent.ExitProcess)
            expectNoEvents()
            cancelAndConsumeRemainingEvents()
        }
    }
}


class FakeAccountStartUiProvider : AccountStartUiProvider {
    override fun options(): List<OnboardingOptionUIModel> = listOf(
        OnboardingOptionUIModel(
            event = AccountEvent.GoToCreatePin,
            titleRes = 1,
            subtitleRes = 2,
            imageRes = 3
        ),
        OnboardingOptionUIModel(
            event = AccountEvent.LinkBank,
            titleRes = 4,
            subtitleRes = 5,
            imageRes = 6
        ),
        OnboardingOptionUIModel(
            event = AccountEvent.SavingGoals,
            titleRes = 7,
            subtitleRes = 8,
            imageRes = 9
        )
    )

    override fun exitButton(): Int = 10
}