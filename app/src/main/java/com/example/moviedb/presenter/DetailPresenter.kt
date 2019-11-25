package com.example.moviedb.presenter

import com.example.moviedb.factory.MovieFactory
import com.example.moviedb.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(private val view: DetailContract.View) : DetailContract.Presenter {
    private val movieFactory = MovieFactory()

    override fun getDetails(movieId: Int) {
        movieFactory.getDetails(movieId).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                response.body()?.let {
                    view.displayDetails(it)
                }
            }
        })
    }

}