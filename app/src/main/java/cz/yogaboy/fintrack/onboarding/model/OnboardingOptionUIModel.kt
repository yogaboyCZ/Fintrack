package cz.yogaboy.fintrack.onboarding.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import cz.yogaboy.fintrack.onboarding.OnboardingEvent

data class OnboardingOptionUIModel(
    val event: OnboardingEvent,
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    @DrawableRes val imageRes: Int
)