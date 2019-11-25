package com.example.moviedb.presenter

import com.example.moviedb.factory.MovieFactory
import com.example.moviedb.model.ConfigurationResponse
import com.example.moviedb.model.GenreResponse
import com.example.moviedb.model.PopularResponse
import com.example.moviedb.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

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

    override fun getConfig() {
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

}