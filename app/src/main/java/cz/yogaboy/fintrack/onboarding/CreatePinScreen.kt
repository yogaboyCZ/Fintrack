package cz.yogaboy.fintrack.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.yogaboy.fintrack.R
import cz.yogaboy.fintrack.ui.theme.FintrackTheme

@Composable
fun CreatePinRoute(
    navController: NavController,
    vm: CreatePinViewModel = viewModel()
) {
    val state = vm.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        vm.uiEffect.collect { effect ->
            when (effect) {
                CreatePinEffect.NavigateBack -> navController.popBackStack()
                is CreatePinEffect.PinCreated -> {}// prepared for next step
            }
        }
    }

    CreatePinScreen(
        state = state,
        onEvent = vm::handle,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePinScreen(
    state: CreatePinUiState,
    onEvent: (CreatePinEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.onb_create_pin_title),
                        style = FintrackTheme.textStyles.titleM,
                        color = FintrackTheme.colors.onBackground,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(CreatePinEvent.BackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                modifier = Modifier.padding(bottom = FintrackTheme.dimens.xlarge),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FintrackTheme.colors.background,
                    titleContentColor = FintrackTheme.colors.onBackground,
                    navigationIconContentColor = FintrackTheme.colors.onBackground,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = FintrackTheme.dimens.xlarge),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.large),
            ) {
                Text(
                    text = stringResource(R.string.onb_create_pin_helper),
                    style = FintrackTheme.textStyles.contentL,
                    color = FintrackTheme.colors.onSurfaceVariant,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = FintrackTheme.dimens.xxlarge),
                    horizontalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.default),
                ) {
                    repeat(4) { index ->
                        val filled = state.digits[index] != null
                        val showDigit = filled && state.maskDigit[index]

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(FintrackTheme.dimens.radiusSmall),
                            color = FintrackTheme.colors.surfaceVariant,
                            border = BorderStroke(
                                1.dp,
                                if (filled) FintrackTheme.colors.primary else FintrackTheme.colors.outline
                            )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                when {
                                    showDigit -> Text(
                                        text = state.digits[index].toString(),
                                        style = FintrackTheme.textStyles.titleM,
                                        color = FintrackTheme.colors.onSurface,
                                    )

                                    filled -> Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .background(FintrackTheme.colors.onSurface, CircleShape),
                                    )

                                    else -> Unit
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            PinKeypad(
                onDigit = { onEvent(CreatePinEvent.DigitPressed(it)) },
                onBackspace = { onEvent(CreatePinEvent.BackspacePressed) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.weight(1f))

            Button(
                onClick = { onEvent(CreatePinEvent.SubmitPressed) },
                enabled = state.isComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = FintrackTheme.dimens.xlarge),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FintrackTheme.colors.primary,
                    contentColor = FintrackTheme.colors.onPrimary,
                )
            ) {
                Text(
                    stringResource(R.string.onb_btn_create_pin),
                    style = FintrackTheme.textStyles.contentL,
                )
            }
        }
    }

}

@Composable
private fun PinKeypad(
    onDigit: (Int) -> Unit,
    onBackspace: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        val rows = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
        )

        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = FintrackTheme.dimens.tiny),
                horizontalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.tiny)
            ) {
                row.forEach { n ->
                    Button(
                        onClick = { onDigit(n) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(FintrackTheme.dimens.radiusMedium),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FintrackTheme.colors.surfaceVariant,
                            contentColor = FintrackTheme.colors.onSurface,
                        )
                    ) {
                        Text("$n", style = FintrackTheme.textStyles.titleM)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = FintrackTheme.dimens.tiny),
            horizontalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.tiny)
        ) {
            Spacer(Modifier.weight(1f))

            Button(
                onClick = { onDigit(0) },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(FintrackTheme.dimens.radiusMedium),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FintrackTheme.colors.surfaceVariant,
                    contentColor = FintrackTheme.colors.onSurface
                )
            ) {
                Text("0", style = FintrackTheme.textStyles.titleM)
            }

            Button(
                onClick = onBackspace,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(FintrackTheme.dimens.radiusMedium),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FintrackTheme.colors.surfaceVariant,
                    contentColor = FintrackTheme.colors.onSurface
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.Backspace, contentDescription = null)
            }
        }
    }
}


//@Preview (showBackground = true)
//@Composable
//fun CreatePinScreenPreview() {
//    val state = OnboardingUiState(
//        options = listOf(
//            OnboardingOptionUIModel(
//                event = OnboardingEvent.GoToCreatePin,
//                titleRes = R.string.app_name,
//                subtitleRes = R.string.app_name,
//                imageRes = R.drawable.ic_launcher_background
//            )
//        ),
//        exitButton = R.string.app_name
//    )
//    CreatePinScreen(state = state, onClick = {})
//}

@Preview(showBackground = true)
@Composable
private fun VectorOnlyPreview() {
    val iv = ImageVector.vectorResource(id = R.drawable.ic_launcher_background)
    val painter = rememberVectorPainter(image = iv)
    Image(painter = painter, contentDescription = null)
}

