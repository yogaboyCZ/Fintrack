package cz.yogaboy.account

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import cz.yogaboy.account.model.OnboardingOptionUIModel
import cz.yogaboy.ui.R as LR
import cz.yogaboy.ui.R.drawable as DR

internal class AccountStartPreviewProvider : PreviewParameterProvider<AccountUiState> {
    override val values: Sequence<AccountUiState> = sequenceOf(
        AccountUiState(
            options = listOf(
                OnboardingOptionUIModel(
                    event = AccountEvent.GoToCreatePin,
                    titleRes = LR.string.onb_start_title_pin,
                    subtitleRes = LR.string.onb_start_subtitle_pin,
                    imageRes = DR.ic_wallet
                ),
                OnboardingOptionUIModel(
                    event = AccountEvent.LinkBank,
                    titleRes = LR.string.onb_start_title_link,
                    subtitleRes = LR.string.onb_start_subtitle_link,
                    imageRes = DR.ic_trading
                ),
                OnboardingOptionUIModel(
                    event = AccountEvent.SavingGoals,
                    titleRes = LR.string.onb_start_title_goal,
                    subtitleRes = LR.string.onb_start_subtitle_goal,
                    imageRes = DR.ic_growing_money
                )
            ),
        )
    )
}
