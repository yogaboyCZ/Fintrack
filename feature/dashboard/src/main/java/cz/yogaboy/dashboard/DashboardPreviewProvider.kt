package cz.yogaboy.dashboard.preview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import cz.yogaboy.dashboard.DashboardUiState
import cz.yogaboy.dashboard.model.FinancialCard
import cz.yogaboy.dashboard.model.NavigationItem
import cz.yogaboy.dashboard.model.TransactionActivity
import cz.yogaboy.ui.R as LR
import cz.yogaboy.dashboard.R as DR

internal class DashboardPreviewProvider : PreviewParameterProvider<DashboardUiState> {
    override val values: Sequence<DashboardUiState> = sequenceOf(
        DashboardUiState(
            userName = "Jane",
            navigationItems = listOf(
                NavigationItem(
                    labelRes = LR.string.nav_home,
                    iconRes = DR.drawable.ic_nav_home,
                ),
                NavigationItem(
                    labelRes = LR.string.nav_budgets,
                    iconRes = DR.drawable.ic_nav_budgets,
                ),
                NavigationItem(
                    labelRes = LR.string.nav_savings,
                    iconRes = DR.drawable.ic_nav_savings,
                ),
                NavigationItem(
                    labelRes = LR.string.nav_expenses,
                    iconRes = DR.drawable.ic_nav_expenses,
                ),
                NavigationItem(
                    labelRes = LR.string.nav_account,
                    iconRes = DR.drawable.ic_nav_account,
                ),
            ),
            financialCards = listOf(
                FinancialCard(
                    title = "Account balance",
                    amount = "₦ 1,000,500.55",
                    subtitle = "The total balance from your linked accounts.",
                    actionText = "Manage Accounts",
                    backgroundCardImg = 0,
                    backgroundColor = Color(0xFF2D6B5E),
                    progressPercentage = null
                ),
                FinancialCard(
                    title = "Total savings",
                    amount = "₦ 50,530.00",
                    subtitle = "You need ₦250,000 to meet your targets.",
                    actionText = "View Savings",
                    backgroundCardImg = 0,
                    backgroundColor = Color(0xFFE67E22),
                    progressPercentage = 0.20f
                ),
                FinancialCard(
                    title = "Monthly budget",
                    amount = "₦ 1,000,500.55",
                    subtitle = "left out of ₦200,000,000 budgeted.",
                    actionText = "Manage Budget",
                    backgroundCardImg = 0,
                    backgroundColor = Color(0xFF34495E),
                    progressPercentage = 0.005f
                ),
                FinancialCard(
                    title = "Total expenses",
                    amount = "₦ 1,000,500.55",
                    subtitle = "spent in the last 7 days",
                    actionText = "View expenses",
                    backgroundCardImg = 0,
                    backgroundColor = Color(0xFFE74C3C),
                    progressPercentage = 0.75f
                )
            ),
            transactions = listOf(
                TransactionActivity(
                    title = "Created a new budget \"Trip to Nairobi\"",
                    subtitle = "a day ago",
                    amount = "₦ 200,000.00",
                    timeAgo = "Today, 14/07/2024",
                    iconLetter = "B",
                    iconColor = Color(0xFF3498DB),
                ),
                TransactionActivity(
                    title = "John Ogaga",
                    subtitle = "Zenith Bank 12:03 AM",
                    amount = "₦ 10,000.00",
                    timeAgo = "Yesterday, 14/07/2024",
                    iconLetter = "J",
                    iconColor = Color(0xFF2ECC71),
                ),
                // 13/07/2024
                TransactionActivity(
                    title = "Payment to Green Market",
                    subtitle = "Groceries",
                    amount = "₦ 5,420.00",
                    timeAgo = "Saturday, 13/07/2024",
                    iconLetter = "G",
                    iconColor = Color(0xFF27AE60),
                ),
                TransactionActivity(
                    title = "Electricity Bill",
                    subtitle = "Power Holdings",
                    amount = "₦ 14,300.00",
                    timeAgo = "Saturday, 13/07/2024",
                    iconLetter = "E",
                    iconColor = Color(0xFFE67E22),
                ),
                TransactionActivity(
                    title = "Received from Tomi Adesina",
                    subtitle = "Refund for tickets",
                    amount = "₦ 12,000.00",
                    timeAgo = "Saturday, 13/07/2024",
                    iconLetter = "T",
                    iconColor = Color(0xFF2980B9),
                ),
                // 12/07/2024
                TransactionActivity(
                    title = "Netflix Subscription",
                    subtitle = "Monthly renewal",
                    amount = "₦ 3,200.00",
                    timeAgo = "Friday, 12/07/2024",
                    iconLetter = "N",
                    iconColor = Color(0xFFE74C3C),
                ),
                TransactionActivity(
                    title = "Uber Ride",
                    subtitle = "Home to Airport",
                    amount = "₦ 7,850.00",
                    timeAgo = "Friday, 12/07/2024",
                    iconLetter = "U",
                    iconColor = Color(0xFF8E44AD),
                ),
                TransactionActivity(
                    title = "Coffee House",
                    subtitle = "Morning latte",
                    amount = "₦ 1,200.00",
                    timeAgo = "Friday, 12/07/2024",
                    iconLetter = "C",
                    iconColor = Color(0xFF16A085),
                ),
            ),
            selectedTab = 0
        )
    )
}