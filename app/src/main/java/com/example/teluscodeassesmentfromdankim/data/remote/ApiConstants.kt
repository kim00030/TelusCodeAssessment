package com.example.teluscodeassesmentfromdankim.data.remote

/**
 * Author: Dan Kim
 *
 * Normally, API keys should be stored securely (e.g., in local.properties and accessed via BuildConfig)
 * to avoid exposing them in public repositories.
 *
 * For this code assessment, the TMDB API key is included here as a constant for convenience,
 * so reviewers can clone and run the project without additional setup.
 *
 * Example of secure usage (not applied here):
 *   - Store key in `local.properties`: tmdb_api_key=YOUR_API_KEY
 *   - Access via: BuildConfig.TMDB_API_KEY
 */
object ApiConstants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val PEOPLE_ID = 71580
    const val API_KEY = "fde80ec0240b6d85418e79eb66a01117" //Exposed intentionally for assessment
}