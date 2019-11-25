package com.example.moviedb.presenter

import android.util.Log
import com.example.moviedb.factory.MovieFactory
import com.example.moviedb.model.Genre
import com.example.moviedb.model.GenreResponse
import com.example.moviedb.model.PopularResponse
import com.example.moviedb.model.Result
import com.example.moviedb.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val movieFactory = MovieFactory()

    override fun getMovies(page: Int) {

        movieFactory.getPopular(page).enqueue(object : Callback<PopularResponse>{

            override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
                Logger.error(t)
            }

            override fun onResponse(
                call: Call<PopularResponse>,
                response: Response<PopularResponse>
            ) {
                response.body()?.let { popularResponse ->

                    movieFactory.getGenres().enqueue(object : Callback<GenreResponse>{
                        override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                            Logger.error(t)
                        }

                        override fun onResponse(
                            call: Call<GenreResponse>,
                            response: Response<GenreResponse>
                        ) {
                            response.body()?.let {genreResponse ->
                                view.displayMovies(popularResponse.results, genreResponse.genres)
                            }
                        }
                    })

                }
            }

        })

    }

}