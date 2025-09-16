package cz.yogaboy.fintrack.onboarding

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import cz.yogaboy.fintrack.R
import cz.yogaboy.fintrack.onboarding.model.OnboardingOptionUIModel

internal class OnboardingStartPreviewProvider : PreviewParameterProvider<OnboardingUiState> {
    override val values: Sequence<OnboardingUiState> = sequenceOf(
        OnboardingUiState(
            options = listOf(
                OnboardingOptionUIModel(
                    event = OnboardingEvent.GoToCreatePin,
                    titleRes = R.string.onb_start_title_pin,
                    subtitleRes = R.string.onb_start_subtitle_pin,
                    imageRes = R.drawable.ic_preview_wallet_24
                ),
                OnboardingOptionUIModel(
                    event = OnboardingEvent.LinkBank,
                    titleRes = R.string.onb_start_title_link,
                    subtitleRes = R.string.onb_start_subtitle_link,
                    imageRes = R.drawable.ic_preview_wallet_24
                ),
                OnboardingOptionUIModel(
                    event = OnboardingEvent.SavingGoals,
                    titleRes = R.string.onb_start_title_goal,
                    subtitleRes = R.string.onb_start_subtitle_goal,
                    imageRes = R.drawable.ic_preview_wallet_24
                )
            ),
            exitButton = R.string.onb_start_button_skip
        )
    )
}
