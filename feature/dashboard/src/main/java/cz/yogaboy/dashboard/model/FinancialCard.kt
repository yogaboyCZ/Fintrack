package cz.yogaboy.dashboard.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class FinancialCard(
    val title: String,
    val amount: String,
    val subtitle: String,
    val actionText: String,
    @param:DrawableRes val backgroundCardImg: Int,
    val backgroundColor: Color,
    val textColor: Color = Color.White,
    val progressPercentage: Float? = null
)