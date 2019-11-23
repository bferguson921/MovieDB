package com.example.moviedb.factory

import com.example.moviedb.model.PopularResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    fun getPopular(@Query("api_key")apiKey: String, @Query("page") page: Int) : Call<PopularResponse>
}