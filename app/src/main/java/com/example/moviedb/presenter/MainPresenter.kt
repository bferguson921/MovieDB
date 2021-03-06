package com.example.moviedb.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.moviedb.factory.MovieFactory
import com.example.moviedb.model.*
import com.example.moviedb.util.Logger
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val view: MainContract.View, private val okHttpClient: OkHttpClient) : MainContract.Presenter {

    private val movieFactory = MovieFactory(okHttpClient)

    override fun getPopularMovies(page: Int) {

        movieFactory.getPopular(page).enqueue(object : Callback<PopularResponse>{

            override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
                Logger.error(t)
            }

            override fun onResponse(
                call: Call<PopularResponse>,
                response: Response<PopularResponse>
            ) {
                response.body()?.let {
                    view.displayMovies(it.results)
                }
            }
        })

    }

    override fun setImageConfig() {
        movieFactory.getConfiguration().enqueue(object : Callback<ConfigurationResponse> {
            override fun onFailure(call: Call<ConfigurationResponse>, t: Throwable) {
                Logger.error(t)
            }

            override fun onResponse(
                call: Call<ConfigurationResponse>,
                response: Response<ConfigurationResponse>
            ) {
                response.body()?.let {
                    view.getImages(it.images)
                }
            }
        })

    }

    override fun setGenres() {


        movieFactory.getGenres().enqueue(object : Callback<GenreResponse> {
            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                Logger.error(t)
            }

            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                response.body()?.let {
                    view.getGenres(it.genres)
                }
            }
        })

    }

    override fun searchForMovie(movie: String) {
        movieFactory.searchMovie(movie).enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Logger.error(t)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                response.body()?.let {
                    view.displaySearch(it.results)
                }
            }

        })
    }



}