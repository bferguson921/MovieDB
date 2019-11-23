package com.example.moviedb.factory

import com.example.moviedb.model.PopularResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFactory {
    private lateinit var moveService: MovieService

    private val BASE_URL = "https://api.themoviedb.org/3"

    init {
        moveService = createMovieService(getRetrofitInstance())
    }

    private fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createMovieService(retrofit: Retrofit) : MovieService {
        return retrofit.create(MovieService::class.java)
    }

    fun getPopular(page: Int) : Call<PopularResponse> {
        return moveService.getPopular("api_key", page)
    }
}