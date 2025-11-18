package cz.yogaboy.dashboard.domain

import cz.yogaboy.dashboard.model.FinancialCard
import cz.yogaboy.dashboard.model.NavigationItem
import cz.yogaboy.dashboard.model.TransactionActivity

interface DashboardProvider {
    fun userName(): String
    fun navigation(): List<NavigationItem>
    fun cards(): List<FinancialCard>
    fun transactions(): List<TransactionActivity>
}