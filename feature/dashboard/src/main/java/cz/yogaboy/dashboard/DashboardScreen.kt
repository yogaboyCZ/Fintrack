package cz.yogaboy.dashboard

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.yogaboy.dashboard.model.FinancialCard
import cz.yogaboy.dashboard.model.TransactionActivity
import cz.yogaboy.dashboard.preview.DashboardPreviewProvider
import cz.yogaboy.ui.theme.FintrackTheme
import org.koin.compose.viewmodel.koinViewModel
import cz.yogaboy.dashboard.R as DR
import cz.yogaboy.ui.R as LR

@Composable
fun DashboardRoute() {
    val vm: DashboardViewModel = koinViewModel()
    DashboardScreen(
        state = vm.uiState.collectAsStateWithLifecycle().value,
        onEvent = vm::handleEvent
    )
}

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    onEvent: (DashboardEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        Header(state, onEvent)
        Spacer(modifier = Modifier.height(FintrackTheme.dimens.medium))
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.large),
            contentPadding = PaddingValues(horizontal = FintrackTheme.dimens.default),
        ) {
            item { FinancialCardsRow(state.financialCards) }
            item { YourWeekCard(onEvent) }
            item { RecentActivitiesCard(state.transactions, onEvent) }
            item { Spacer(modifier = Modifier.height(FintrackTheme.dimens.medium)) }
        }

        BottomNav(state, onEvent)
    }
}

@Composable
private fun Header(
    state: DashboardUiState,
    onEvent: (DashboardEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = FintrackTheme.dimens.default,
                vertical = FintrackTheme.dimens.medium,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Hello, ${state.userName}",
                style = FintrackTheme.textStyles.titleM,
                color = FintrackTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(LR.string.dashboard_subtitle),
                style = FintrackTheme.textStyles.contentL,
                color = FintrackTheme.colors.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = DR.drawable.ic_user),
                contentDescription = null,
                tint = FintrackTheme.colors.onSurface,
                modifier = Modifier
                    .size(FintrackTheme.dimens.iconSize)
                    .clickable { onEvent(DashboardEvent.ViewAllActivitiesClicked) },
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = DR.drawable.ic_bell),
                contentDescription = null,
                tint = FintrackTheme.colors.onSurface,
                modifier = Modifier
                    .size(FintrackTheme.dimens.iconSize)
                    .clickable { onEvent(DashboardEvent.ViewAllActivitiesClicked) },
            )
        }
    }
}

@Composable
private fun FinancialCardsRow(cards: List<FinancialCard>) {
    val listState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // Calculate the current visible card index based on scroll position
    // Using derivedStateOf to avoid recomposition on every scroll event
    val currentIndex by remember {
        derivedStateOf {
            val firstVisibleItem = listState.layoutInfo.visibleItemsInfo.firstOrNull()
            if (firstVisibleItem != null) {
                val itemSize = firstVisibleItem.size
                val offset = listState.firstVisibleItemScrollOffset
                val index = listState.firstVisibleItemIndex

                // If scrolled more than half of the item, consider next item as current
                if (offset > itemSize / 2) {
                    (index + 1).coerceIn(0, cards.size - 1)
                } else {
                    index.coerceIn(0, cards.size - 1)
                }
            } else {
                0
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.medium),
            flingBehavior = snapBehavior,
        ) {
            items(cards) { card -> FinancialCardItem(card) }
        }

        Spacer(modifier = Modifier.height(FintrackTheme.dimens.medium))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            cards.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .width(if (index == currentIndex) 32.dp else 8.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(
                            if (index == currentIndex) {
                                FintrackTheme.colors.primary
                            } else {
                                FintrackTheme.colors.onSurface.copy(alpha = 0.2f)
                            },
                        ),
                )
            }
        }
    }
}

@Composable
private fun YourWeekCard(
    onEvent: (DashboardEvent) -> Unit,
) {
    Column(
        modifier = Modifier
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = FintrackTheme.colors.surface),
            shape = RoundedCornerShape(FintrackTheme.dimens.radiusMedium),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(FintrackTheme.dimens.large),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.small),
                ) {
                    Text(
                        text = "Your Week",
                        style = FintrackTheme.textStyles.titleM,
                        color = FintrackTheme.colors.onSurface,
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("in ")
                            withStyle(
                                style = androidx.compose.ui.text.SpanStyle(
                                    fontSize = FintrackTheme.textStyles.titleL.fontSize,
                                    fontWeight = FintrackTheme.textStyles.titleL.fontWeight,
                                    fontFamily = FintrackTheme.textStyles.titleL.fontFamily,
                                    color = Color(0xFFE67E22),
                                ),
                            ) {
                                append("Money")
                            }
                        },
                        style = FintrackTheme.textStyles.titleM,
                        color = FintrackTheme.colors.onSurface,
                    )

                    Text(
                        text = "See your past week in money",
                        style = FintrackTheme.textStyles.contentM,
                        color = FintrackTheme.colors.onSurfaceVariant,
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD8EBEB))
                        .clickable { onEvent(DashboardEvent.ViewAllActivitiesClicked) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = DR.drawable.ic_chevron_right),
                        contentDescription = null,
                        tint = Color(0xFF2D6B5E),
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentActivitiesCard(
    items: List<TransactionActivity>,
    onEvent: (DashboardEvent) -> Unit,
) {
    Column(modifier = Modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = FintrackTheme.colors.surface),
            shape = RoundedCornerShape(FintrackTheme.dimens.radiusLarge),
        ) {
            Column(modifier = Modifier.padding(FintrackTheme.dimens.large)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Recent Activities",
                        style = FintrackTheme.textStyles.sectionHeader,
                        color = FintrackTheme.colors.onBackground,
                    )
                    Button(
                        onClick = { onEvent(DashboardEvent.ViewAllActivitiesClicked) },
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier.height(36.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD8EBEB),
                            contentColor = Color(0xFF2D6B5E),
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 0.dp,
                        ),
                    ) {
                        Text(
                            text = "View All",
                            style = FintrackTheme.textStyles.contentM,
                            color = Color(0xFF2D6B5E),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = DR.drawable.ic_chevron_right),
                            contentDescription = null,
                            tint = Color(0xFF2D6B5E),
                            modifier = Modifier.size(12.dp),
                        )
                    }
                }
                Spacer(Modifier.height(FintrackTheme.dimens.large))

                items
                    .groupBy { it.timeAgo }
                    .forEach { (date, grouped) ->
                        Text(
                            text = date,
                            style = FintrackTheme.textStyles.contentM,
                            color = FintrackTheme.colors.onSurfaceVariant,
                        )
                        Spacer(Modifier.height(FintrackTheme.dimens.small))
                        grouped.forEachIndexed { index, trans ->
                            TransactionActivityItem(transActivity = trans)
                            if (index != grouped.lastIndex) {
                                Spacer(Modifier.height(FintrackTheme.dimens.medium))
                            }
                        }
                        Spacer(Modifier.height(FintrackTheme.dimens.large))
                    }
            }
        }
    }
}

@Composable
private fun BottomNav(
    state: DashboardUiState,
    onEvent: (DashboardEvent) -> Unit,
) {
    NavigationBar(
        containerColor = FintrackTheme.colors.surface,
        contentColor = FintrackTheme.colors.onSurface,
    ) {
        state.navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = state.selectedTab == index,
                onClick = { onEvent(DashboardEvent.TabSelected(index)) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = item.iconRes),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.labelRes),
                        style = FintrackTheme.textStyles.contentM,
                    )
                },
            )
        }
    }
}

@Composable
private fun FinancialCardItem(
    card: FinancialCard,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .width(FintrackTheme.dimens.cardWidth)
            .height(FintrackTheme.dimens.cardHeight),
        colors = CardDefaults.cardColors(containerColor = card.backgroundColor),
        shape = RoundedCornerShape(FintrackTheme.dimens.radiusLarge),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (card.backgroundCardImg != 0) {
                Image(
                    painter = painterResource(id = card.backgroundCardImg),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                        card.textColor.copy(alpha = 0.3f),
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(FintrackTheme.dimens.large),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = card.title,
                        style = FintrackTheme.textStyles.contentM,
                        color = card.textColor.copy(alpha = 0.8f),
                    )
                    Button(
                        onClick = { /* TODO: Add action */ },
                        modifier = Modifier.height(28.dp),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = card.textColor.copy(alpha = 0.9f),
                            contentColor = card.backgroundColor,
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 10.dp,
                            vertical = 0.dp,
                        ),
                    ) {
                        Text(
                            text = card.actionText,
                            style = FintrackTheme.textStyles.contentXS,
                            color = card.backgroundColor,
                            maxLines = 1,
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = DR.drawable.ic_chevron_right),
                            contentDescription = null,
                            tint = card.backgroundColor,
                            modifier = Modifier.size(11.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(FintrackTheme.dimens.medium))

                Column(
                    verticalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.tiny),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.small),
                    ) {
                        Text(
                            text = card.amount,
                            style = FintrackTheme.textStyles.titleM.copy(fontWeight = FontWeight.Bold),
                            color = card.textColor,
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(id = DR.drawable.ic_eye),
                            contentDescription = "Toggle visibility",
                            tint = card.textColor.copy(alpha = 0.6f),
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    Text(
                        text = card.subtitle,
                        style = FintrackTheme.textStyles.contentM,
                        color = card.textColor.copy(alpha = 0.8f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (card.progressPercentage != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(card.textColor.copy(alpha = 0.2f)),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(card.progressPercentage)
                                .clip(RoundedCornerShape(2.dp))
                                .background(card.textColor),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionActivityItem(
    transActivity: TransactionActivity,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(FintrackTheme.dimens.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(transActivity.iconColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = transActivity.iconLetter,
                style = FintrackTheme.textStyles.contentM.copy(fontWeight = FontWeight.Bold),
                color = transActivity.iconColor,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transActivity.title,
                style = FintrackTheme.textStyles.contentL,
                color = FintrackTheme.colors.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = transActivity.subtitle,
                style = FintrackTheme.textStyles.contentM,
                color = FintrackTheme.colors.onSurfaceVariant,
            )
        }
        Text(
            text = transActivity.amount,
            style = FintrackTheme.textStyles.contentL.copy(fontWeight = FontWeight.Medium),
            color = FintrackTheme.colors.onSurface,
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DashboardScreenPreview(
    @PreviewParameter(DashboardPreviewProvider::class) state: DashboardUiState,
) {
    FintrackTheme {
        DashboardScreen(
            state = state,
            onEvent = {},
        )
    }
}