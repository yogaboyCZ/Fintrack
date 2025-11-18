package cz.yogaboy.dashboard.model

import androidx.compose.ui.graphics.Color

data class TransactionActivity(
    val title: String,
    val subtitle: String,
    val amount: String,
    val timeAgo: String,
    val iconLetter: String,
    val iconColor: Color
)