package cz.yogaboy.account

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import cz.yogaboy.account.model.OnboardingOptionUIModel
import cz.yogaboy.ui.R as LR
import cz.yogaboy.ui.R.drawable as DR

internal class OnboardingStartPreviewProvider : PreviewParameterProvider<OnboardingUiState> {
    override val values: Sequence<OnboardingUiState> = sequenceOf(
        OnboardingUiState(
            options = listOf(
                OnboardingOptionUIModel(
                    event = OnboardingEvent.GoToCreatePin,
                    titleRes = LR.string.onb_start_title_pin,
                    subtitleRes = LR.string.onb_start_subtitle_pin,
                    imageRes = DR.ic_preview_wallet_24
                ),
                OnboardingOptionUIModel(
                    event = OnboardingEvent.LinkBank,
                    titleRes = LR.string.onb_start_title_link,
                    subtitleRes = LR.string.onb_start_subtitle_link,
                    imageRes = DR.ic_preview_wallet_24
                ),
                OnboardingOptionUIModel(
                    event = OnboardingEvent.SavingGoals,
                    titleRes = LR.string.onb_start_title_goal,
                    subtitleRes = LR.string.onb_start_subtitle_goal,
                    imageRes = DR.ic_preview_wallet_24
                )
            ),
            exitButton = LR.string.onb_start_button_skip
        )
    )
}
