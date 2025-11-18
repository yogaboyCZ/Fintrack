package cz.yogaboy.dashboard

import androidx.lifecycle.ViewModel
import cz.yogaboy.dashboard.domain.DashboardProvider
import cz.yogaboy.dashboard.model.FinancialCard
import cz.yogaboy.dashboard.model.NavigationItem
import cz.yogaboy.dashboard.model.TransactionActivity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface DashboardEvent {
    data class TabSelected(val index: Int) : DashboardEvent
    data object ViewAllActivitiesClicked : DashboardEvent
    data object ViewProfileClicked : DashboardEvent
    data object ViewNotificationsClicked : DashboardEvent
    data object ManageAccountsClicked : DashboardEvent
    data object ViewSavingsClicked : DashboardEvent
    data object ManageBudgetClicked : DashboardEvent
    data object ViewExpensesClicked : DashboardEvent
}

data class DashboardUiState(
    val userName: String = "Jane",
    val navigationItems: List<NavigationItem> = emptyList(),
    val financialCards: List<FinancialCard> = emptyList(),
    val transactions: List<TransactionActivity> = emptyList(),
    val selectedTab: Int = 0
)

sealed interface DashboardUiEffect {
    data object NavigateToDashboard : DashboardUiEffect
}

class DashboardViewModel(
    private val provider: DashboardProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        DashboardUiState(
            userName = provider.userName(),
            navigationItems = provider.navigation(),
            financialCards = provider.cards(),
            transactions = provider.transactions(),
            selectedTab = 0,
        )
    )
    val uiState: StateFlow<DashboardUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<DashboardUiEffect>()
    val uiEffect: SharedFlow<DashboardUiEffect> = _uiEffect

    fun handleEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.TabSelected -> _uiState.value =
                _uiState.value.copy(selectedTab = event.index)

            DashboardEvent.ViewAllActivitiesClicked -> {}
            DashboardEvent.ManageAccountsClicked -> {}
            DashboardEvent.ViewSavingsClicked -> {}
            DashboardEvent.ManageBudgetClicked -> {}
            DashboardEvent.ViewExpensesClicked -> {}
            DashboardEvent.ViewNotificationsClicked -> {}
            DashboardEvent.ViewProfileClicked -> {}
        }
    }
}