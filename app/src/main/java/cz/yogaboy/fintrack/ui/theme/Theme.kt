package cz.yogaboy.fintrack.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

object FintrackTheme {
    val colors: FintrackColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val textStyles: FintrackTextStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalTextStyles.current

    val dimens: Dimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current

    @Composable
    operator fun invoke(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = false, //Personally I hate disabling dynamic color coz they made whole system nicer
        content: @Composable () -> Unit
    ) {
        val context = LocalContext.current
        val materialScheme =
            if (dynamicColor) {
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                val base = if (darkTheme) fintrackDarkColors() else fintrackLightColors()
                base.toMaterialScheme(darkTheme)
            }

        val appColors =
            if (dynamicColor) remember(materialScheme) { materialScheme.asFintrackColors() }
            else remember(darkTheme) { if (darkTheme) fintrackDarkColors() else fintrackLightColors() }

        val appTextStyles = remember { fintrackTextStyles(AppFontFamily) }

        CompositionLocalProvider(
            LocalColors provides appColors,
            LocalDimens provides Dimens(),
            LocalTextStyles provides appTextStyles,
        ) {
            MaterialTheme(
                colorScheme = materialScheme,
                typography = FintrackTypography,
                content = content,
            )
        }
    }
}