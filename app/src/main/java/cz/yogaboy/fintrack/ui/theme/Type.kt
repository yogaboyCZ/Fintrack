package cz.yogaboy.fintrack.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import cz.yogaboy.fintrack.R

@OptIn(ExperimentalTextApi::class)
private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@OptIn(ExperimentalTextApi::class)
val AppFontFamily: FontFamily = FontFamily(
    Font(GoogleFont("Instrument Sans"), fontProvider, FontWeight.Normal),
    Font(GoogleFont("Instrument Sans"), fontProvider, FontWeight.Medium),
    Font(GoogleFont("Instrument Sans"), fontProvider, FontWeight.SemiBold),
    Font(GoogleFont("Instrument Sans"), fontProvider, FontWeight.Bold)
)
private val base = Typography()

val FintrackTypography = Typography(
    displayLarge = base.displayLarge.copy(fontFamily = AppFontFamily),
    displayMedium = base.displayMedium.copy(fontFamily = AppFontFamily),
    displaySmall = base.displaySmall.copy(fontFamily = AppFontFamily),
    headlineLarge = base.headlineLarge.copy(fontFamily = AppFontFamily),
    headlineMedium = base.headlineMedium.copy(fontFamily = AppFontFamily),
    headlineSmall = base.headlineSmall.copy(fontFamily = AppFontFamily),
    titleLarge = base.titleLarge.copy(fontFamily = AppFontFamily),
    titleMedium = base.titleMedium.copy(fontFamily = AppFontFamily),
    titleSmall = base.titleSmall.copy(fontFamily = AppFontFamily),
    bodyLarge = base.bodyLarge.copy(fontFamily = AppFontFamily),
    bodyMedium = base.bodyMedium.copy(fontFamily = AppFontFamily),
    bodySmall = base.bodySmall.copy(fontFamily = AppFontFamily),
    labelLarge = base.labelLarge.copy(fontFamily = AppFontFamily),
    labelMedium = base.labelMedium.copy(fontFamily = AppFontFamily),
    labelSmall = base.labelSmall.copy(fontFamily = AppFontFamily)
)