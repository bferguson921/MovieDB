package com.example.moviedb.factory

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.moviedb.BuildConfig
import com.example.moviedb.model.*
import com.example.moviedb.util.Logger
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFactory(private val okHttpClient: OkHttpClient) {
    private val movieService: MovieService

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val API_KEY = BuildConfig.CONSUMER_KEY

    init {
        movieService = createMovieService(getRetrofitInstance())
    }

    private fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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

    fun searchMovie(movie: String) : Call<SearchResponse> {
        Logger.debug("movie search")
        return movieService.searchForMovie(API_KEY, movie)
    }
}