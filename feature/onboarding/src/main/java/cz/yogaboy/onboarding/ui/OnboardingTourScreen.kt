package cz.yogaboy.onboarding.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.yogaboy.ui.theme.FintrackTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import cz.yogaboy.onboarding.R.drawable as DR
import cz.yogaboy.ui.R as LR

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingTourRoute(
    viewModel: OnboardingTourViewModel = viewModel(),
    onCreateAccount: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.pageCount },
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collectLatest { currentPage ->
                viewModel.handle(OnboardingTourEvent.PageChanged(currentPage))
            }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnboardingTourEffect.NavigateToCreateAccount -> onCreateAccount()
            }
        }
    }

    OnboardingTourScreen(
        state = state,
        onEvent = viewModel::handle,
        pagerState = pagerState
    )
}

private data class OnbPage(
    val titleRes: Int,
    val subtitleRes: Int,
    val imageRes: Int,
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnboardingTourScreen(
    state: OnboardingTourState,
    onEvent: (OnboardingTourEvent) -> Unit,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val pages = listOf(
        OnbPage(
            titleRes = LR.string.onb_tour_1_title,
            subtitleRes = LR.string.onb_tour_1_subtitle,
            imageRes = DR.ic_trading,
        ),
        OnbPage(
            titleRes = LR.string.onb_tour_2_title,
            subtitleRes = LR.string.onb_tour_2_subtitle,
            imageRes = DR.ic_growing_money,
        ),
        OnbPage(
            titleRes = LR.string.onb_tour_3_title,
            subtitleRes = LR.string.onb_tour_3_subtitle,
            imageRes = DR.ic_financial,
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = FintrackTheme.dimens.large),
    ) {
        TopStepIndicator(
            pagerState = pagerState,
            pageCount = state.pageCount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp,
                    bottom = FintrackTheme.dimens.large,
                ),
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                snapPositionalThreshold = 0.90f,
            ),
        ) { pageIndex ->
            val page = pages[pageIndex]
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.large),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(page.titleRes),
                    style = FintrackTheme.textStyles.titleL,
                    color = FintrackTheme.colors.onBackground,
                )
                Text(
                    text = stringResource(page.subtitleRes),
                    style = FintrackTheme.textStyles.contentL,
                    color = FintrackTheme.colors.onSurfaceVariant,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = page.imageRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = FintrackTheme.dimens.xlarge),
            verticalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.default),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = { onEvent(OnboardingTourEvent.ShowBottomSheet) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FintrackTheme.colors.primary,
                    contentColor = FintrackTheme.colors.onPrimary,
                ),
                shape = RoundedCornerShape(FintrackTheme.dimens.radiusLarge),
            ) {
                Text(
                    text = stringResource(LR.string.onb_tour_btn_create),
                    style = FintrackTheme.textStyles.contentL,
                )
            }

            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(LR.string.onb_tour_have_account),
                    style = FintrackTheme.textStyles.contentM,
                    color = FintrackTheme.colors.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(FintrackTheme.dimens.tiny))
                Text(
                    text = stringResource(LR.string.onb_tour_sign_in),
                    style = FintrackTheme.textStyles.contentM,
                    color = FintrackTheme.colors.primary,
                    modifier = Modifier.clickable {
                        onEvent(OnboardingTourEvent.NavigateToCreateAccount)
                    },
                )
            }
        }
    }

    if (state.showSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val scope = rememberCoroutineScope()

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onEvent(OnboardingTourEvent.BottomSheetDismissed) },
            shape = RoundedCornerShape(
                topStart = FintrackTheme.dimens.radiusLarge,
                topEnd = FintrackTheme.dimens.radiusLarge,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = FintrackTheme.dimens.xlarge,
                        vertical = FintrackTheme.dimens.large,
                    ),
                verticalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.default),
            ) {
                Text(
                    text = stringResource(LR.string.onb_sheet_title),
                    style = FintrackTheme.textStyles.titleM,
                    color = FintrackTheme.colors.onBackground,
                )
                Text(
                    text = stringResource(LR.string.onb_sheet_body),
                    style = FintrackTheme.textStyles.contentM,
                    color = FintrackTheme.colors.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(FintrackTheme.dimens.large))
                Button(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onEvent(OnboardingTourEvent.NavigateToCreateAccount)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FintrackTheme.colors.primary,
                        contentColor = FintrackTheme.colors.onPrimary,
                    ),
                    shape = RoundedCornerShape(FintrackTheme.dimens.radiusLarge),
                ) {
                    Text(
                        text = stringResource(LR.string.onb_sheet_btn),
                        style = FintrackTheme.textStyles.contentL,
                    )
                }
                Spacer(modifier = Modifier.height(FintrackTheme.dimens.large))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopStepIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
) {
    val barHeight = 6.dp
    val barSpacing = FintrackTheme.dimens.small
    val barShape = RoundedCornerShape(100)

    val trackColor = FintrackTheme.colors.outlineVariant
    val activeColor = FintrackTheme.colors.onBackground

    BoxWithConstraints(modifier = modifier) {
        val totalSpacing = barSpacing * (pageCount - 1)
        val segmentWidth = (maxWidth - totalSpacing) / pageCount

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(barSpacing),
        ) {
            repeat(pageCount) {
                Box(
                    Modifier
                        .width(segmentWidth)
                        .height(barHeight)
                        .clip(barShape)
                        .background(trackColor)
                )
            }
        }

        val density = LocalDensity.current
        val current = (pagerState.currentPage + pagerState.currentPageOffsetFraction)
            .coerceIn(0f, (pageCount - 1).toFloat())

        val segmentWidthPx = with(density) { segmentWidth.toPx() }
        val spacingPx = with(density) { barSpacing.toPx() }
        val activeOffsetX = (current * (segmentWidthPx + spacingPx))

        Box(
            Modifier
                .offset { IntOffset(activeOffsetX.roundToInt(), 0) }
                .width(segmentWidth)
                .height(barHeight)
                .clip(barShape)
                .background(activeColor)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun OnboardingTourScreenPreview() {
    val state = OnboardingTourState(
        pageCount = 3,
        currentPage = 0,
        showSheet = false
    )
    val pagerState = rememberPagerState(pageCount = { state.pageCount })
    OnboardingTourScreen(
        state = state,
        onEvent = {},
        pagerState = pagerState
    )
}