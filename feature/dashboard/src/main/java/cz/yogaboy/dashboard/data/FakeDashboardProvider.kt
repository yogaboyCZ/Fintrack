package cz.yogaboy.dashboard.data

import androidx.compose.ui.graphics.Color
import cz.yogaboy.dashboard.R as DR
import cz.yogaboy.ui.R as LR
import cz.yogaboy.dashboard.domain.DashboardProvider
import cz.yogaboy.dashboard.model.FinancialCard
import cz.yogaboy.dashboard.model.NavigationItem
import cz.yogaboy.dashboard.model.TransactionActivity

class FakeDashboardProvider : DashboardProvider {
    override fun userName() = "Jane"

    override fun navigation() = listOf(
        NavigationItem(LR.string.nav_home, DR.drawable.ic_nav_home),
        NavigationItem(LR.string.nav_budgets, DR.drawable.ic_nav_budgets),
        NavigationItem(LR.string.nav_savings, DR.drawable.ic_nav_savings),
        NavigationItem(LR.string.nav_expenses, DR.drawable.ic_nav_expenses),
        NavigationItem(LR.string.nav_account, DR.drawable.ic_nav_account)
    )

    override fun cards() = listOf(
        FinancialCard(
            title = "Account balance",
            amount = "₦ 1,000,500.55",
            subtitle = "The total balance from your linked accounts.",
            actionText = "Manage Accounts",
            backgroundCardImg = DR.drawable.ic_vault,
            backgroundColor = Color(0xFF2D6B5E),
            progressPercentage = null
        ),
        FinancialCard(
            title = "Total savings",
            amount = "₦ 50,530.00",
            subtitle = "You need ₦250,000 to meet your targets.",
            actionText = "View Savings",
            backgroundCardImg = DR.drawable.ic_piggy_bank,
            backgroundColor = Color(0xFFE67E22),
            progressPercentage = 0.20f
        ),
        FinancialCard(
            title = "Monthly budget",
            amount = "₦ 1,000,500.55",
            subtitle = "left out of ₦200,000,000 budgeted.",
            actionText = "Manage Budget",
            backgroundCardImg = DR.drawable.ic_target,
            backgroundColor = Color(0xFF34495E),
            progressPercentage = 0.7f
        ),
        FinancialCard(
            title = "Total expenses",
            amount = "₦ 1,000,500.55",
            subtitle = "spent in the last 7 days",
            actionText = "View expenses",
            backgroundCardImg = DR.drawable.ic_cash,
            backgroundColor = Color(0xFFE74C3C),
            progressPercentage = 0.5f
        )
    )

    override fun transactions() = listOf(
        TransactionActivity(
            title = "Created a new budget \"Trip to Nairobi\"",
            subtitle = "a day ago",
            amount = "₦ 200,000.00",
            timeAgo = "Today, 14/07/2024",
            iconLetter = "B",
            iconColor = Color(0xFF3498DB)
        ),
        TransactionActivity(
            title = "John Ogaga",
            subtitle = "Zenith Bank 12:03 AM",
            amount = "₦ 10,000.00",
            timeAgo = "Yesterday, 13/07/2024",
            iconLetter = "J",
            iconColor = Color(0xFF2ECC71)
        ),
        TransactionActivity(
            title = "Payment to Green Market",
            subtitle = "Groceries",
            amount = "₦ 5,420.00",
            timeAgo = "Saturday, 13/07/2024",
            iconLetter = "G",
            iconColor = Color(0xFF27AE60)
        ),
        TransactionActivity(
            title = "Electricity Bill",
            subtitle = "Power Holdings",
            amount = "₦ 14,300.00",
            timeAgo = "Saturday, 13/07/2024",
            iconLetter = "E",
            iconColor = Color(0xFFE67E22)
        ),
        TransactionActivity(
            title = "Received from Tomi Adesina",
            subtitle = "Refund for tickets",
            amount = "₦ 12,000.00",
            timeAgo = "Saturday, 13/07/2024",
            iconLetter = "T",
            iconColor = Color(0xFF2980B9)
        ),
        TransactionActivity(
            title = "Netflix Subscription",
            subtitle = "Monthly renewal",
            amount = "₦ 3,200.00",
            timeAgo = "Friday, 12/07/2024",
            iconLetter = "N",
            iconColor = Color(0xFFE74C3C)
        ),
        TransactionActivity(
            title = "Uber Ride",
            subtitle = "Home to Airport",
            amount = "₦ 7,850.00",
            timeAgo = "Friday, 12/07/2024",
            iconLetter = "U",
            iconColor = Color(0xFF8E44AD)
        ),
        TransactionActivity(
            title = "Coffee House",
            subtitle = "Morning latte",
            amount = "₦ 1,200.00",
            timeAgo = "Friday, 12/07/2024",
            iconLetter = "C",
            iconColor = Color(0xFF16A085)
        )
    )
}