package cz.yogaboy.account.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import cz.yogaboy.account.OnboardingEvent

data class OnboardingOptionUIModel(
    val event: OnboardingEvent,
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    @DrawableRes val imageRes: Int
)