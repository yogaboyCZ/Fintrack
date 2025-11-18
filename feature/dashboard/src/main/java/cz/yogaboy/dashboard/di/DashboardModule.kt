package cz.yogaboy.dashboard.di

import cz.yogaboy.dashboard.DashboardViewModel
import cz.yogaboy.dashboard.data.FakeDashboardProvider
import cz.yogaboy.dashboard.domain.DashboardProvider
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardModule = module {
    singleOf(::FakeDashboardProvider) { bind<DashboardProvider>() }
    viewModelOf(::DashboardViewModel)
}