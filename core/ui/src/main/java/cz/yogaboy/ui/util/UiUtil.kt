package cz.yogaboy.ui.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

fun String.highlightBrand(
    brand: String,
    color: Color
): AnnotatedString = buildAnnotatedString {
    append(this@highlightBrand)
    val start = indexOf(brand)
    if (start >= 0) {
        addStyle(
            style = SpanStyle(color = color),
            start = start,
            end = start + brand.length
        )
    }
}