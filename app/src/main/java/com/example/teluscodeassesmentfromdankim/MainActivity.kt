package com.example.teluscodeassesmentfromdankim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.teluscodeassesmentfromdankim.presentation.navgraph.NavGraph
import com.example.teluscodeassesmentfromdankim.ui.theme.TelusCodeAssesmentFromDanKimTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 *  * ### API Key Handling:
 *  * - For this coding assessment, the TMDB API key is stored directly in `ApiConstants`.
 *  * - In a real production app, I **do not** store API keys in code or constants files.
 *  * - Instead, I would use a more secure approach like injecting it from `BuildConfig` or reading from environment variables or encrypted storage.
 *  * - This simplification was made intentionally to reduce boilerplate for this assessment and because the TMDB API key is public and non-sensitive.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            TelusCodeAssesmentFromDanKimTheme {
                NavGraph()
            }
        }
    }
}
