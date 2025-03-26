package com.example.teluscodeassesmentfromdankim.data.local.moviedb

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    // Inserts or updates a list of movies in the local database
    @Upsert
    suspend fun upsertMovieList(movies: List<MovieEntity>)
    // Retrieves all movies currently stored in the local database
    @Query("SELECT * FROM MovieEntity")
    suspend fun getMovieList(): List<MovieEntity>
    // Retrieves a single movie by its ID, or null if not found
    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?
}