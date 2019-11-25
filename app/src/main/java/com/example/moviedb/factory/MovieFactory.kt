package com.example.moviedb.factory

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.moviedb.model.*
import com.example.moviedb.util.Logger
import okhttp3.Cache
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

    fun searchMovie(movie: String) : Call<SearchResponse> {
        Logger.debug("movie search")
        return movieService.searchForMovie(API_KEY, movie)
    }


    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}