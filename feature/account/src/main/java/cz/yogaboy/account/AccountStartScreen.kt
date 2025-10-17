package cz.yogaboy.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cz.yogaboy.account.navigation.AccountCreatePin
import cz.yogaboy.ui.theme.FintrackTheme
import cz.yogaboy.ui.util.highlightBrand
import cz.yogaboy.ui.R as LR

@Composable
fun AccountStartRoute(
    navController: NavHostController,
    onExitToDashboard: () -> Unit,
) {
    val vm: AccountViewModel = viewModel()

    LaunchedEffect(Unit) {
        vm.uiEffect.collect { effect ->
            when (effect) {
                AccountUiEffect.NavigateToCreatePin -> {
                    navController.navigate(AccountCreatePin) { launchSingleTop = true }
                }
                AccountUiEffect.NavigateToDashboard -> onExitToDashboard()
            }
        }
    }

    AccountStartScreen(
        state = vm.uiState.collectAsStateWithLifecycle().value,
        event = vm::handleEvent,
    )
}

@Composable
fun AccountStartScreen(
    state: AccountUiState,
    event: (AccountEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = FintrackTheme.dimens.large)
            .padding(top = FintrackTheme.dimens.xlarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OnboardingWelcomeHeader()
        Spacer(modifier = Modifier.height(50.dp))

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
                    modifier = Modifier
                        .shadow(FintrackTheme.dimens.small, shape = shape, clip = false)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(FintrackTheme.dimens.medium),
                    colors = CardDefaults.cardColors(
                        containerColor = FintrackTheme.colors.surface,
                        contentColor = FintrackTheme.colors.onSurface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    onClick = { event(option.event) },
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(FintrackTheme.dimens.large),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(option.titleRes),
                                style = FintrackTheme.textStyles.titleM,
                                color = FintrackTheme.colors.onSurface,
                            )
                            Text(
                                text = stringResource(option.subtitleRes),
                                style = FintrackTheme.textStyles.contentM,
                                color = FintrackTheme.colors.onSurfaceVariant,
                            )
                        }

                        Image(
                            imageVector = ImageVector.vectorResource(option.imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = FintrackTheme.dimens.large)
                                .size(124.dp),
                        )
                    }
                }
            }
        }
        Button(
            onClick = { event(AccountEvent.SkipForNow) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = FintrackTheme.dimens.xlarge),
            colors = ButtonDefaults.buttonColors(
                containerColor = FintrackTheme.colors.primary,
                contentColor = FintrackTheme.colors.onPrimary,
            )
        ) {
            Text(
                text = stringResource(LR.string.onb_start_button_skip),
                style = FintrackTheme.textStyles.contentL,
            )
        }
    }
}

@Composable
private fun OnboardingWelcomeHeader() {
    val brandName = stringResource(LR.string.app_name_brand)
    val title = stringResource(LR.string.onb_start_title_fmt, brandName)
    val subtitle = stringResource(LR.string.onb_start_subtitle)

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

@Preview(showBackground = true)
@Composable
private fun OnboardingStartScreenPreview(
    @PreviewParameter(AccountStartPreviewProvider::class) state: AccountUiState,
) {
    FintrackTheme {
        AccountStartScreen(
            state = state,
            event = {}
        )
    }
}
