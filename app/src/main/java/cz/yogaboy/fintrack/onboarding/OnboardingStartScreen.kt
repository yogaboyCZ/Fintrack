package cz.yogaboy.fintrack.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.yogaboy.fintrack.R
import cz.yogaboy.fintrack.navigation.OnboardingCreatePin
import cz.yogaboy.fintrack.ui.theme.FintrackTheme
import cz.yogaboy.fintrack.ui.util.highlightBrand

@Composable
fun OnboardingStartRoute(
    navController: NavController,
) {
    val vm: OnboardingViewModel = viewModel()

    LaunchedEffect(Unit) {
        vm.uiEffect.collect { effect ->
            when (effect) {
                OnboardingUiEffect.NavigateToCreatePin -> navController.navigate(OnboardingCreatePin.route)
            }
        }
    }

    OnboardingStartScreen(
        state = vm.uiState.collectAsStateWithLifecycle().value,
        onClick = vm::handleEvent
    )
}

@Composable
fun OnboardingStartScreen(
    state: OnboardingUiState,
    onClick: (OnboardingEvent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = FintrackTheme.dimens.large,
                vertical = FintrackTheme.dimens.xlarge,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OnboardingWelcomeHeader()
        Spacer(Modifier.height(50.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            state.options.forEach { option ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(FintrackTheme.dimens.small),
                    colors = CardDefaults.cardColors(),
                    onClick = { onClick(option.event) },
                ) {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(option.titleRes),
                                style = FintrackTheme.textStyles.titleM,
                                color = FintrackTheme.colors.onSurface,
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(option.subtitleRes),
                                style = FintrackTheme.textStyles.contentM,
                                color = FintrackTheme.colors.onSurfaceVariant,
                            )
                        },
                        trailingContent = {
                            Image(
                                imageVector = ImageVector.vectorResource(option.imageRes),
                                contentDescription = null,
                                modifier = Modifier.padding(start = FintrackTheme.dimens.small),
                            )
                        }
                    )
                }
            }
        }
        Button(
            onClick = { onClick(OnboardingEvent.ExitProcess) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = FintrackTheme.colors.primary,
                contentColor = FintrackTheme.colors.onPrimary,
            )
        ) {
            Text(
                text = stringResource(state.exitButton),
                style = FintrackTheme.textStyles.contentL,
            )
        }

    }
}

@Composable
private fun OnboardingWelcomeHeader() {
    val brandName = stringResource(R.string.app_name_brand)
    val title = stringResource(R.string.onb_start_title_fmt, brandName)
    val subtitle = stringResource(R.string.onb_start_subtitle)

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title.highlightBrand(brandName, FintrackTheme.colors.primary),
            style = FintrackTheme.textStyles.titleM.copy(
                color = FintrackTheme.colors.onBackground,
            ),
            textAlign = TextAlign.Start,
        )
        Text(
            text = subtitle,
            style = FintrackTheme.textStyles.titleM.copy(
                color = FintrackTheme.colors.onSurfaceVariant,
            ),
            textAlign = TextAlign.Start,
        )
    }
}

// DON'T know why Preview with painterResource crashes
@Preview(showBackground = true)
@Composable
private fun OnboardingStartScreenPreview(
    @PreviewParameter(OnboardingStartPreviewProvider::class) state: OnboardingUiState
) {
    FintrackTheme {
        OnboardingStartScreen(
            state = state,
            onClick = {}
        )
    }
}
// DON'T know why Preview with painterResource crashes
//@Preview(showBackground = true)
//@Composable
//private fun VectorOnlyPreview() {
//    Icon(
//        painter = painterResource(R.drawable.ic_preview_wallet_24),
//        contentDescription = null
//    )
//}