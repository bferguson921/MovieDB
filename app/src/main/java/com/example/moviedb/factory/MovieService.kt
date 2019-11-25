package com.example.moviedb.factory

import com.example.moviedb.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    fun getPopular(@Query("api_key")apiKey: String, @Query("page") page: Int) : Call<PopularResponse>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key")apiKey: String) : Call<GenreResponse>

    @GET("movie/{movie_id}")
    fun getDetails(@Path("movie_id")movie: Int, @Query("api_key") apiKey: String) : Call<MovieResponse>

    @GET("configuration")
    fun getConfiguration(@Query("api_key") apiKey: String) : Call<ConfigurationResponse>

    @GET("search/movie")
    fun searchForMovie(@Query("api_key") apiKey: String, @Query("query")movie: String) : Call<SearchResponse>
}