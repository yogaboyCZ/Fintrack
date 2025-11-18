package cz.yogaboy.dashboard.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NavigationItem(
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int
)