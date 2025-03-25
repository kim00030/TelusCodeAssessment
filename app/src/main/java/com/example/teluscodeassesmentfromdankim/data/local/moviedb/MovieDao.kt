package com.example.teluscodeassesmentfromdankim.data.local.moviedb

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovieList(movies: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity")
    suspend fun getMovieList(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?
}