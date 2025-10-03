plugins {
    id("cz.yogaboy.android-library")
    id("cz.yogaboy.compose-conventions")
}

android {
    namespace = "cz.yogaboy.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui.text.google.fonts)
}