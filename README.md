# **Main Entry Point of the App**

Project Overview
 * This app fulfills the movie browsing requirements based on the TMDB API, including:
### Requirements Completed:

1. **Home Screen**:
 * Lists all movies for a given actor (Benedict Cumberbatch) using a paginated API call.
 
2. **Detail Screen**:
 *  Navigates to a detail view when a movie is tapped.
 *  Displays movie title, poster image, and full overview.
 
3. **Type-Safe Navigation**:
 *   Navigation between screens is implemented using a type-safe `ScreenRoute` sealed class.
 
4. **Code Organization & Architecture**:
 * MVVM architecture using Hilt for dependency injection.
 *    Retrofit for networking, Room for local storage (Home movies only).
 *    Repository pattern separates data layer from ViewModels.

6. **Optional Feature - Similar Movies**:
 * Implemented a horizontally scrollable **LazyRow** showing similar movies.
 * Tapping a similar movie opens a fullscreen dialog with its details.
 *  Fully separated repository and ViewModel used for maintainability.
 
 * ### 🧪 Unit Tests Implemented:
 *  `HomeViewModelTest`
 *  `DetailsViewModelTest`
 *  `MovieListRepositoryImplTest`
 *  `MovieMapperKtTest` (DTO/Entity mappers)
