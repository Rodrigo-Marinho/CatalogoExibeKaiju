package com.rodrigo.catalogo.network

import com.rodrigo.catalogo.network.MovieEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("3/search/movie")
    fun getMonsterverseCollection(
        @Query("query") query: String,
        @Query("language") language: String
    ): Call<MovieResponse>
}