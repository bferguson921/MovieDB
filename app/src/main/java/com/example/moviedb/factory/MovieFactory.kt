package com.example.moviedb.factory

import com.example.moviedb.model.ConfigurationResponse
import com.example.moviedb.model.GenreResponse
import com.example.moviedb.model.MovieResponse
import com.example.moviedb.model.PopularResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFactory {
    private val movieService: MovieService

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val API_KEY = "ef141c7b48a3291d3916eda5f1cfa9ad"

    init {
        movieService = createMovieService(getRetrofitInstance())
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
        return movieService.getPopular(API_KEY, page)
    }

    fun getGenres() : Call<GenreResponse>{
        return movieService.getGenres(API_KEY)
    }

    fun getDetails(movieId: Int) : Call<MovieResponse> {
        return movieService.getDetails(movieId, API_KEY)
    }

    fun getConfiguration() : Call<ConfigurationResponse> {
        return movieService.getConfiguration(API_KEY)
    }
}