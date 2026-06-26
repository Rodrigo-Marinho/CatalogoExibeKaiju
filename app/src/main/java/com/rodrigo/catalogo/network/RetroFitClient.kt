package com.rodrigo.catalogo.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {

        private const val API_KEY = "Bearer"
        private lateinit var instance: Retrofit

        fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", API_KEY)
                        .addHeader("accept", "application/json")
                        .build()
                    chain.proceed(request)
                }

            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = Retrofit.Builder()
                        .client(httpClient.build())
                        .baseUrl("https://api.themoviedb.org/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return instance
            }
        }

        fun createMovieService(): MovieService {
            return getRetrofitInstance().create(MovieService::class.java)
        }
    }
}