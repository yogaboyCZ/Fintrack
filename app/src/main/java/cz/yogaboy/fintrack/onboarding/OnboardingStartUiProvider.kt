package cz.yogaboy.fintrack.onboarding

import androidx.annotation.StringRes
import cz.yogaboy.fintrack.R
import cz.yogaboy.fintrack.onboarding.model.OnboardingOptionUIModel

interface OnboardingStartUiProvider {
    fun options(): List<OnboardingOptionUIModel>
    @StringRes fun exitButton(): Int
}

class DefaultOnboardingStartUiProvider : OnboardingStartUiProvider {
    override fun options(): List<OnboardingOptionUIModel> = listOf(
        OnboardingOptionUIModel(
            event = OnboardingEvent.GoToCreatePin,
            titleRes = R.string.onb_start_title_pin,
            subtitleRes = R.string.onb_start_subtitle_pin,
            imageRes = R.drawable.ic_wallet,
        ),
        OnboardingOptionUIModel(
            event = OnboardingEvent.LinkBank,
            titleRes = R.string.onb_start_title_link,
            subtitleRes = R.string.onb_start_subtitle_link,
            imageRes = R.drawable.ic_trading,
        ),
        OnboardingOptionUIModel(
            event = OnboardingEvent.SavingGoals,
            titleRes = R.string.onb_start_title_goal,
            subtitleRes = R.string.onb_start_subtitle_goal,
            imageRes = R.drawable.ic_growing_money,
        )
    )

    @StringRes
    override fun exitButton(): Int = R.string.onb_start_button_skip
}