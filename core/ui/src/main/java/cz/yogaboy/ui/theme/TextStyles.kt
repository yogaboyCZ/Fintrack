package cz.yogaboy.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class FintrackTextStyles(
    val titleL: TextStyle,
    val titleM: TextStyle,
    val sectionHeader: TextStyle,
    val contentL: TextStyle,
    val contentM: TextStyle,
    val contentS: TextStyle,
    val contentXS: TextStyle,
    val caption: TextStyle,
)

private val DefaultTextStyles = FintrackTextStyles(
    titleL = TextStyle(
        fontSize = 32.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.Normal
    ),
    titleM = TextStyle(
        fontSize = 20.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Normal
    ),
    sectionHeader = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal
    ),
    contentL = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal
    ),
    contentM = TextStyle(
        fontSize = 14.sp,
        lineHeight = 21.sp,
        fontWeight = FontWeight.Normal
    ),
    contentS = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    contentXS = TextStyle(
        fontSize = 10.sp,
        lineHeight = 15.sp,
        fontWeight = FontWeight.Normal
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Normal
    ),
)

val LocalTextStyles = staticCompositionLocalOf { DefaultTextStyles }

fun fintrackTextStyles(fontFamily: FontFamily): FintrackTextStyles =
    FintrackTextStyles(
        titleL = DefaultTextStyles.titleL.copy(fontFamily = fontFamily),
        titleM = DefaultTextStyles.titleM.copy(fontFamily = fontFamily),
        sectionHeader = DefaultTextStyles.sectionHeader.copy(fontFamily = fontFamily),
        contentL = DefaultTextStyles.contentL.copy(fontFamily = fontFamily),
        contentM = DefaultTextStyles.contentM.copy(fontFamily = fontFamily),
        contentS = DefaultTextStyles.contentS.copy(fontFamily = fontFamily),
        contentXS = DefaultTextStyles.contentXS.copy(fontFamily = fontFamily),
        caption = DefaultTextStyles.caption.copy(fontFamily = fontFamily),
    )