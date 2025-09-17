package cz.yogaboy.fintrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cz.yogaboy.fintrack.navigation.RootNavGraph
import cz.yogaboy.ui.theme.FintrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FintrackTheme {
                Surface(color = FintrackTheme.colors.background) {
                    RootNavGraph()
                }
            }
        }
    }
}