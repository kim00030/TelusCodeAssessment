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
 * Main entry point of the app.
 *
 * ## Project Overview
 * This app fulfills the movie browsing requirements based on the TMDB API, including:
 *
 * ### Requirements Completed:
 * 1. **Home Screen**:
 *    - Lists all movies for a given actor (Benedict Cumberbatch) using a paginated API call.
 *
 * 2. **Detail Screen**:
 *    - Navigates to a detail view when a movie is tapped.
 *    - Displays movie title, poster image, and full overview.
 *
 * 3. **Type-Safe Navigation**:
 *    - Navigation between screens is implemented using a type-safe `ScreenRoute` sealed class.
 *
 * 4. **Error & Placeholder Handling**:
 *    - Gracefully handles missing image data with a placeholder image.
 *    - Handles loading and error states via centralized state management.
 *
 * 5. **Code Organization & Architecture**:
 *    - MVVM architecture using Hilt for dependency injection.
 *    - Retrofit for networking, Room for local storage (Home movies only).
 *    - Repository pattern separates data layer from ViewModels.
 *
 * 6. **Optional Feature - Similar Movies**:
 *    - Implemented a horizontally scrollable **LazyRow** showing similar movies.
 *    - Tapping a similar movie opens a fullscreen dialog with its details.
 *    - Fully separated repository and ViewModel used for maintainability.
 *
 * ### 🧪 Unit Tests Implemented:
 * - `HomeViewModelTest`
 * - `DetailsViewModelTest`
 * - `MovieListRepositoryImplTest`
 * - `MovieMapperKtTest` (DTO/Entity mappers)
 *
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
